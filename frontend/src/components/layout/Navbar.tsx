import { Menu, Search, ShoppingBag } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../features/auth/AuthContext';
import { useCart } from '../../features/cart/CartContext';
import { Logo } from './Logo';

export function Navbar(){const {count}=useCart();const {session,logout}=useAuth();const initials=session?.email.slice(0,2).toUpperCase()??'IN';return <header className="navbar"><Logo/><nav><Link to="/">Home</Link><Link to="/products">Products</Link><Link to="/categories">Categories</Link><Link to="/products">Deals</Link>{session?.role==='ADMIN'&&<Link to="/admin" style={{color:'var(--brand)',fontWeight:600}}>Admin Dashboard</Link>}</nav><label className="search"><Search size={18}/><input placeholder="Search products..." /></label><Link className="cart" to="/cart" aria-label="Shopping cart"><ShoppingBag size={21}/>{count>0&&<b>{count}</b>}</Link>{session?<Link className="profile" to="/profile"><div>{initials}</div><span>{session.email}<small><button onClick={event=>{event.preventDefault();event.stopPropagation();logout();}}>Sign out</button></small></span></Link>:<Link className="sign-in" to="/login">Sign in</Link>}<button className="menu" aria-label="Open menu"><Menu/></button></header>}
