import { ArrowRight, Play } from 'lucide-react';
import { useEffect, useState } from 'react';
import { Footer } from '../components/layout/Footer';
import { Navbar } from '../components/layout/Navbar';
import { Button } from '../components/ui/Button';
import { SectionHeader } from '../components/ui/SectionHeader';
import { catalogApi } from '../features/catalog/api';
import { CategoryCard } from '../features/catalog/CategoryCard';
import { ProductCard } from '../features/catalog/ProductCard';
import type { Category, Product } from '../types/catalog';

export function HomePageDatabase() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [failed, setFailed] = useState(false);

  useEffect(() => {
    const loadCatalog = () => Promise.all([catalogApi.categories(), catalogApi.featured()])
      .then(([loadedCategories, loadedProducts]) => {
        setCategories(loadedCategories);
        setProducts(loadedProducts);
        setFailed(false);
      })
      .catch(() => setFailed(true));
    void loadCatalog();
    window.addEventListener('shoply:catalog-updated', loadCatalog);
    return () => window.removeEventListener('shoply:catalog-updated', loadCatalog);
  }, []);

  return <><Navbar /><main id="top">
    <section className="hero"><div><em>SUMMER COLLECTION DROP</em><h1>Curate Your Space With Modern Classics</h1><p>Explore our hand-picked summer arrivals designed to blend premium tech productivity with absolute Scandinavian comfort. Enjoy free global delivery.</p><div className="hero-actions"><Button>Shop Collection <ArrowRight size={18} /></Button><Button variant="secondary"><Play size={17} fill="currentColor" /> Watch Video tour</Button></div></div></section>
    <section id="categories" className="content-section"><SectionHeader title="Browse by Category" subtitle="Locate premium curated hardware, apparel, and lifestyle items." action="See All Categories" />{failed ? <p className="load-error">The catalog could not be loaded. Start the Shoply API and refresh.</p> : <div className="category-grid">{categories.map((category) => <CategoryCard key={category.id} category={category} />)}</div>}</section>
    <section id="products" className="content-section products"><SectionHeader title="Featured Arrivals" subtitle="High demand, premium design, and implementation ready." />{!failed && <div className="product-grid">{products.map((product) => <ProductCard key={product.id} product={product} />)}</div>}</section>
  </main><Footer /></>;
}
