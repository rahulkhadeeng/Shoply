import { FormEvent, useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { Pencil, Trash2, X, Plus } from 'lucide-react';
import { AdminShell } from '../components/layout/AdminShell';
import { Button } from '../components/ui/Button';
import { useAuth } from '../features/auth/AuthContext';
import { catalogApi } from '../features/catalog/api';
import type { Category } from '../types/catalog';
import { API_BASE } from '../services/api/client';

export function AdminCategoriesPage() {
  const { session } = useAuth();
  const [categories, setCategories] = useState<Category[]>([]);
  const [editingCategory, setEditingCategory] = useState<Category | null>(null);
  const [name, setName] = useState('');
  const [error, setError] = useState('');
  const token = session?.token ?? '';

  const load = () => catalogApi.categories().then(setCategories).catch(() => setError('Unable to load categories.'));

  useEffect(() => {
    if (session?.role === 'ADMIN') load();
  }, [session]);

  useEffect(() => {
    if (editingCategory) {
      setName(editingCategory.name);
    } else {
      setName('');
    }
    setError('');
  }, [editingCategory]);

  if (!session) return <Navigate to="/login" replace />;
  if (session.role !== 'ADMIN') return <Navigate to="/profile" replace />;

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setError('');

    if (!name.trim()) return;

    const slugify = (val: string) => val.trim().toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '');
    const slug = slugify(name);
    const icon = editingCategory?.icon || 'Laptop';

    try {
      const url = editingCategory
        ? `${API_BASE}/admin/categories/${editingCategory.id}`
        : `${API_BASE}/admin/categories`;

      const method = editingCategory ? 'PUT' : 'POST';

      const response = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` },
        body: JSON.stringify({ name, slug, icon })
      });

      if (!response.ok) throw new Error();

      setName('');
      setEditingCategory(null);
      load();
    } catch {
      setError(editingCategory
        ? 'Category update failed. Ensure the name and slug are unique.'
        : 'Category creation failed. Ensure the name and slug are unique.'
      );
    }
  }

  async function remove(id: string) {
    if (!window.confirm('Delete this category? Products assigned to it must be moved first.')) return;
    try {
      const response = await fetch(`${API_BASE}/admin/categories/${id}`, { method: 'DELETE', headers: { Authorization: `Bearer ${token}` } });
      if (!response.ok) throw new Error();
      load();
    } catch {
      setError('This category could not be deleted because it may still contain products.');
    }
  }

  function handleCancel() {
    setEditingCategory(null);
    setName('');
    setError('');
  }

  return (
    <AdminShell>
      <main className="admin-canvas">
        <div className="categories-layout">
          {/* Form Side */}
          <div className="categories-form-card">
            <h3>{editingCategory ? 'Edit Category' : 'Create Category'}</h3>
            <p>
              {editingCategory
                ? 'Modify the name of the selected category.'
                : 'Add a new category to the database catalog.'}
            </p>
            <form className="categories-form" onSubmit={handleSubmit}>
              <label>
                Category Name
                <input
                  type="text"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                  maxLength={120}
                  placeholder="e.g. Home Decor"
                />
              </label>
              {error && <p className="form-error">{error}</p>}
              <div className="categories-form-actions">
                {editingCategory ? (
                  <>
                    <Button type="button" variant="secondary" onClick={handleCancel}>
                      <X size={14} style={{ marginRight: '4px', verticalAlign: 'middle' }} />
                      Cancel
                    </Button>
                    <Button type="submit">
                      Update
                    </Button>
                  </>
                ) : (
                  <Button type="submit">
                    <Plus size={14} style={{ marginRight: '4px', verticalAlign: 'middle' }} />
                    Create
                  </Button>
                )}
              </div>
            </form>
          </div>

          {/* Table Side */}
          <div className="categories-table-card">
            <h3>Manage Categories</h3>
            <div className="categories-table-wrapper">
              <table className="categories-table">
                <thead>
                  <tr>
                    <th style={{ width: '80px' }}>Id</th>
                    <th>Category Name</th>
                    <th style={{ width: '180px', textAlign: 'right' }}>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {categories.map((category, index) => (
                    <tr key={category.id}>
                      <td className="category-serial-cell">#{index + 1}</td>
                      <td className="category-name-cell">{category.name}</td>
                      <td style={{ textAlign: 'right' }}>
                        <button
                          type="button"
                          className="btn-action-edit"
                          onClick={() => setEditingCategory(category)}
                          title="Edit Category"
                        >
                          <Pencil size={12} /> Edit
                        </button>
                        <button
                          type="button"
                          className="btn-action-delete"
                          onClick={() => remove(category.id)}
                          title="Delete Category"
                        >
                          <Trash2 size={12} /> Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                  {categories.length === 0 && (
                    <tr>
                      <td colSpan={3} style={{ textAlign: 'center', padding: '24px', color: 'var(--muted)' }}>
                        No categories found in database.
                      </td>
                    </tr>
                  )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </main>
    </AdminShell>
  );
}
