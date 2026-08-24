import { type FormEvent, useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { AdminShell } from '../components/layout/AdminShell';
import { Button } from '../components/ui/Button';
import { ProductImagesDropzone } from '../components/ui/ProductImagesDropzone';
import { useAuth } from '../features/auth/AuthContext';
import { catalogApi } from '../features/catalog/api';
import type { Category } from '../types/catalog';

const apiBase = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api';
const slugify = (value: string) => value.trim().toLowerCase().replace(/[^a-z0-9]+/g, '-').replace(/(^-|-$)/g, '');
const uniqueSlug = (name: string) => `${slugify(name)}-${Date.now().toString().slice(-8)}`;

export function AdminProductsPageV2() {
  const { session } = useAuth();
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedImages, setSelectedImages] = useState<File[]>([]);
  const [formVersion, setFormVersion] = useState(0);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [saving, setSaving] = useState(false);

  useEffect(() => { if (session?.role === 'ADMIN') catalogApi.categories().then(setCategories).catch(() => setError('Categories could not be loaded.')); }, [session]);
  if (!session) return <Navigate to="/login" replace />;
  if (session.role !== 'ADMIN') return <Navigate to="/profile" replace />;

  async function submit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault(); if (!session) return; const formElement = event.currentTarget; setSaving(true); setError(''); setMessage('');
    if (!selectedImages.length) { setSaving(false); setError('Select at least one product image.'); return; }
    try {
      const form = new FormData(formElement);
      const uploadData = new FormData(); selectedImages.forEach((image) => uploadData.append('files', image));
      const upload = await fetch(`${apiBase}/admin/uploads/product-images`, { method: 'POST', headers: { Authorization: `Bearer ${session.token}` }, body: uploadData });
      const uploadResult = await upload.json().catch(() => null);
      if (!upload.ok || !uploadResult?.urls?.length) throw new Error(uploadResult?.message ?? 'Image upload failed.');
      const name = String(form.get('name') ?? '');
      const body = { name, slug: uniqueSlug(name), description: form.get('description'), price: Number(form.get('price')), previousPrice: form.get('previousPrice') ? Number(form.get('previousPrice')) : null, rating: Number(form.get('rating')), imageUrl: uploadResult.urls[0], featured: form.get('featured') === 'on', categoryId: form.get('categoryId'), inventoryQuantity: Number(form.get('inventoryQuantity')) };
      const created = await fetch(`${apiBase}/admin/products`, { method: 'POST', headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${session.token}` }, body: JSON.stringify(body) });
      const product = await created.json().catch(() => null);
      if (!created.ok || !product?.id) throw new Error(product?.message ?? 'Product could not be saved.');
      const imagesSaved = await fetch(`${apiBase}/admin/products/${product.id}/images`, { method: 'POST', headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${session.token}` }, body: JSON.stringify({ imageUrls: uploadResult.urls }) });
      if (!imagesSaved.ok) { const imageError = await imagesSaved.json().catch(() => null); throw new Error(imageError?.message ?? 'Product was created, but its image list could not be saved.'); }
      formElement.reset(); setSelectedImages([]); setFormVersion((version) => version + 1); window.dispatchEvent(new Event('shoply:catalog-updated')); setMessage('Product and all images were saved successfully. It is now available in the catalog.');
    } catch (cause) { setError(cause instanceof Error ? cause.message : 'Product creation failed.'); } finally { setSaving(false); }
  }

  return <AdminShell><main className="admin-canvas"><section className="admin-table-card admin-product-create">
    <header><div><p className="eyebrow">PRODUCTS <span>›</span> ADD PRODUCT</p><h2>Add New Product</h2></div><div className="admin-create-actions"><Button variant="secondary" type="reset" form="product-create-form">Cancel</Button><Button type="submit" form="product-create-form" disabled={saving}>{saving ? 'Saving…' : 'Save Draft'}</Button></div></header>
    <form id="product-create-form" className="admin-form" onSubmit={submit}><h3>Product Information</h3><div className="form-grid">
      <label className="full">Product Name<input name="name" required placeholder="e.g. Pro Audio Studio Headphones" /></label>
      <label className="full">Description<textarea name="description" required placeholder="Enter product specifications, features, and key benefits…" /></label>
      <label>Price ($)<input name="price" required type="number" min="0" step="0.01" /></label><label>Compare Price ($)<input name="previousPrice" type="number" min="0" step="0.01" /></label>
      <label>Stock Quantity<input name="inventoryQuantity" required type="number" min="0" defaultValue="0" /></label><label>Category<select name="categoryId" required defaultValue=""><option value="" disabled>Select a category</option>{categories.map((category) => <option key={category.id} value={category.id}>{category.name}</option>)}</select></label>
      <label>Customer Rating<input name="rating" required type="number" min="0" max="5" step="0.1" defaultValue="4.5" /></label>
    </div><ProductImagesDropzone key={formVersion} onChange={setSelectedImages} /><p className="upload-help">Your selected images will upload securely to UploadThing when you save this product.</p><label className="featured"><input name="featured" type="checkbox" /> Show as featured product</label>{error && <p className="form-error">{error}</p>}{message && <p className="form-success">{message}</p>}<div className="admin-form-footer"><Button variant="secondary" type="reset">Discard</Button><Button type="submit" disabled={saving}>{saving ? 'Creating…' : 'Add Product'}</Button></div></form>
  </section></main></AdminShell>;
}
