import { useEffect, useState } from 'react';
import { Footer } from '../components/layout/Footer';
import { Navbar } from '../components/layout/Navbar';
import { SectionHeader } from '../components/ui/SectionHeader';
import { catalogApi } from '../features/catalog/api';
import { CategoryCard } from '../features/catalog/CategoryCard';
import type { Category } from '../types/catalog';

export function CategoriesPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [failed, setFailed] = useState(false);
  useEffect(() => { catalogApi.categories().then(setCategories).catch(() => setFailed(true)); }, []);
  return <><Navbar /><main className="catalog-page"><SectionHeader title="Browse by Category" subtitle="Explore the live categories managed in your Shoply database." />{failed ? <p className="catalog-message">The categories could not be loaded. Start the Shoply API and refresh.</p> : <div className="category-grid">{categories.map((category) => <CategoryCard key={category.id} category={category} />)}</div>}</main><Footer /></>;
}
