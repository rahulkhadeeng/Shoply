import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from 'react';
import type { Product } from '../../types/catalog';
import { catalogApi } from '../catalog/api';
import { useAuth } from '../auth/AuthContext';
import { cartApi, type ServerCart } from './api';
import type { CartItem } from './types';

type CartContextValue={items:CartItem[];count:number;total:number;loading:boolean;add:(product:Product,quantity?:number)=>void;setQuantity:(id:string,quantity:number)=>void;remove:(id:string)=>void;clear:()=>void};
const CartContext=createContext<CartContextValue|undefined>(undefined); const storageKey='shoply-cart';
const readGuest=()=>{try{return JSON.parse(localStorage.getItem(storageKey)??'[]') as CartItem[]}catch{return []}};
export function CartProvider({children}:{children:ReactNode}){const {session}=useAuth();const [items,setItems]=useState<CartItem[]>(readGuest);const [loading,setLoading]=useState(false);
  const hydrate=async(server:ServerCart)=>{const hydrated=await Promise.all(server.items.map(async line=>({product:await catalogApi.product(line.productId),quantity:line.quantity})));setItems(hydrated)};
  useEffect(()=>{if(!session){setItems(readGuest());return;}setLoading(true);cartApi.get(session.token).then(hydrate).catch(()=>{}).finally(()=>setLoading(false));},[session?.token]);
  useEffect(()=>{if(!session)localStorage.setItem(storageKey,JSON.stringify(items));},[items,session]);
  const localAdd=(product:Product,quantity:number)=>setItems(current=>{const found=current.find(i=>i.product.id===product.id);return found?current.map(i=>i.product.id===product.id?{...i,quantity:i.quantity+quantity}:i):[...current,{product,quantity}]});
  const value=useMemo(()=>({items,loading,count:items.reduce((n,i)=>n+i.quantity,0),total:items.reduce((n,i)=>n+i.product.price*i.quantity,0),add(product:Product,quantity=1){localAdd(product,quantity);if(session)cartApi.add(session.token,product.id,quantity).then(hydrate).catch(()=>{});},setQuantity(id:string,quantity:number){setItems(current=>current.flatMap(i=>i.product.id===id?(quantity>0?[{...i,quantity}]:[]):[i]));if(session){if(quantity>0)cartApi.update(session.token,id,quantity).then(hydrate).catch(()=>{});else cartApi.remove(session.token,id).then(hydrate).catch(()=>{});}},remove(id:string){setItems(current=>current.filter(i=>i.product.id!==id));if(session)cartApi.remove(session.token,id).then(hydrate).catch(()=>{});},clear(){setItems([]);}}),[items,loading,session]);
  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}
export function useCart(){const cart=useContext(CartContext);if(!cart)throw new Error('useCart must be used inside CartProvider');return cart;}
