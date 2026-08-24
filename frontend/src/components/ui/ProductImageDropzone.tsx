import { ImagePlus, Trash2, UploadCloud } from 'lucide-react';
import '../../styles/upload.css';
import { useId, useState, type DragEvent, type ChangeEvent } from 'react';

type ProductImageDropzoneProps = {
  value?: string;
  onChange: (image: File | null) => void;
};

export function ProductImageDropzone({ value, onChange }: ProductImageDropzoneProps) {
  const inputId = useId();
  const [preview, setPreview] = useState(value ?? '');
  const [isDragging, setIsDragging] = useState(false);

  const selectFile = (file?: File) => {
    if (!file) return;
    if (!file.type.startsWith('image/')) return;
    if (preview.startsWith('blob:')) URL.revokeObjectURL(preview);
    setPreview(URL.createObjectURL(file));
    onChange(file);
  };
  const drop = (event: DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    setIsDragging(false);
    selectFile(event.dataTransfer.files[0]);
  };
  const change = (event: ChangeEvent<HTMLInputElement>) => selectFile(event.target.files?.[0]);
  const remove = () => {
    if (preview.startsWith('blob:')) URL.revokeObjectURL(preview);
    setPreview('');
    onChange(null);
  };

  return <div className="product-image-field">
    <label>Product Images</label>
    {preview ? <div className="image-upload-preview"><img src={preview} alt="New product preview" /><button type="button" onClick={remove} aria-label="Remove image"><Trash2 size={17} /></button></div> : <label htmlFor={inputId} className={`image-dropzone ${isDragging ? 'image-dropzone--active' : ''}`} onDragEnter={() => setIsDragging(true)} onDragLeave={() => setIsDragging(false)} onDragOver={(event) => event.preventDefault()} onDrop={drop}><UploadCloud size={28} /><strong>Drag and drop your product images here</strong><span>Support for JPEG, PNG, WebP (recommended 1200 × 1000px)</span><span className="image-dropzone__browse"><ImagePlus size={15} /> Browse files</span><input id={inputId} type="file" accept="image/jpeg,image/png,image/webp" onChange={change} hidden /></label>}
  </div>;
}
