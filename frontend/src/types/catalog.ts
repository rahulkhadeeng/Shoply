export interface Category { id:string; name:string; slug:string; icon:string; itemCount:number }
export interface Product { id:string; name:string; slug:string; description:string; price:number; previousPrice:number|null; rating:number; imageUrl:string; imageUrls:string[]; featured:boolean; category:Category }
