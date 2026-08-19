import { Search, SlidersHorizontal } from 'lucide-react';
import { useEffect, useMemo, useState } from 'react';
import { Footer } from '../components/layout/Footer';
import { Navbar } from '../components/layout/Navbar';
import { ProductCard } from '../features/catalog/ProductCard';
import { catalogApi } from '../features/catalog/api';
import type { Category, Product } from '../types/catalog';

export function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([]); const [categories, setCategories] = useState<Category[]>([]);
  const [query, setQuery] = useState(''); const [category, setCategory] = useState('all'); const [loading, setLoading] = useState(true); const [failed, setFailed] = useState(false);
  useEffect(() => { Promise.all([catalogApi.all(), catalogApi.categories()]).then(([p,c]) => {setProducts(p);setCategories(c)}).catch(()=>setFailed(true)).finally(()=>setLoading(false)); }, []);
  const visible = useMemo(() => products.filter(p => (category === 'all' || p.category.slug === category) && p.name.toLowerCase().includes(query.toLowerCase())), [products, category, query]);
  return <><Navbar/><main className="catalog-page"><p className="eyebrow">SHOPLY CATALOG</p><h1>Thoughtfully curated, built for everyday.</h1><p className="catalog-intro">Explore premium goods selected for your workspace, wardrobe, and home.</p><div className="catalog-tools"><label><Search size={18}/><input value={query} onChange={e=>setQuery(e.target.value)} placeholder="Search our collection"/></label><select value={category} onChange={e=>setCategory(e.target.value)} aria-label="Filter by category"><option value="all">All categories</option>{categories.map(c=><option key={c.id} value={c.slug}>{c.name}</option>)}</select><span><SlidersHorizontal size={17}/> {visible.length} products</span></div>{loading?<p className="catalog-message">Loading the collection…</p>:failed?<p className="catalog-message">The catalog could not be loaded. Start the Shoply API and refresh.</p>:visible.length?<div className="product-grid catalog-grid">{visible.map(p=><ProductCard key={p.id} product={p}/>)}</div>:<p className="catalog-message">No products match those filters.</p>}</main><Footer/></>;
}
