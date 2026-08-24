import { FormEvent, useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { AdminShell } from '../components/layout/AdminShell';
import { Button } from '../components/ui/Button';
import { useAuth } from '../features/auth/AuthContext';
import { catalogApi } from '../features/catalog/api';
import type { Category } from '../types/catalog';

const apiBase = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api';

export function AdminCategoriesPage() {
  const { session } = useAuth();
  const [categories, setCategories] = useState<Category[]>([]);
  const [error, setError] = useState('');
  const token = session?.token ?? '';
  const load = () => catalogApi.categories().then(setCategories).catch(() => setError('Unable to load categories.'));

  useEffect(() => { if (session?.role === 'ADMIN') load(); }, [session]);
  if (!session) return <Navigate to="/login" replace />;
  if (session.role !== 'ADMIN') return <Navigate to="/profile" replace />;

  async function create(event: FormEvent<HTMLFormElement>) {
    event.preventDefault(); setError('');
    const form = new FormData(event.currentTarget);
    try {
      const response = await fetch(`${apiBase}/admin/categories`, {
        method: 'POST', headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` },
        body: JSON.stringify({ name: form.get('name'), slug: form.get('slug'), icon: form.get('icon') })
      });
      if (!response.ok) throw new Error();
      event.currentTarget.reset(); load();
    } catch { setError('Category creation failed. Ensure the name and slug are unique.'); }
  }

  async function remove(id: string) {
    if (!window.confirm('Delete this category? Products assigned to it must be moved first.')) return;
    try {
      const response = await fetch(`${apiBase}/admin/categories/${id}`, { method: 'DELETE', headers: { Authorization: `Bearer ${token}` } });
      if (!response.ok) throw new Error();
      load();
    } catch { setError('This category could not be deleted because it may still contain products.'); }
  }

  return <AdminShell><main className="admin-canvas"><section className="admin-table-card">
    <header><div><p className="eyebrow">CATALOG</p><h2>Manage categories</h2></div></header>
    <form className="admin-form category-form" onSubmit={create}><div className="form-grid">
      <label>Name<input name="name" required maxLength={120} /></label>
      <label>Slug<input name="slug" required pattern="[a-z0-9]+(?:-[a-z0-9]+)*" placeholder="home-kitchen" /></label>
      <label className="full">Icon name<input name="icon" required maxLength={40} placeholder="House" /></label>
    </div>{error && <p className="form-error">{error}</p>}<Button type="submit">Create category</Button></form>
    <section className="category-admin-list">{categories.map(category => <article key={category.id}><span><strong>{category.name}</strong><small>{category.slug} · {category.itemCount.toLocaleString()} items</small></span><button onClick={() => remove(category.id)}>Delete</button></article>)}</section>
  </section></main></AdminShell>;
}
