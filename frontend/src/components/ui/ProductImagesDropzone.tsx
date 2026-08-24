import { ImagePlus, Trash2, UploadCloud } from 'lucide-react';
import '../../styles/upload.css';
import { useId, useState, type ChangeEvent, type DragEvent } from 'react';

type ProductImagesDropzoneProps = { onChange: (images: File[]) => void };
const maximumImages = 3;

export function ProductImagesDropzone({ onChange }: ProductImagesDropzoneProps) {
  const inputId = useId();
  const [images, setImages] = useState<File[]>([]);
  const addFiles = (files: FileList | File[]) => {
    const incoming = Array.from(files).filter((file) => file.type.startsWith('image/'));
    const next = [...images, ...incoming].slice(0, maximumImages);
    setImages(next); onChange(next);
  };
  const drop = (event: DragEvent<HTMLLabelElement>) => { event.preventDefault(); addFiles(event.dataTransfer.files); };
  const change = (event: ChangeEvent<HTMLInputElement>) => { if (event.target.files) addFiles(event.target.files); event.target.value = ''; };
  const remove = (index: number) => { const next = images.filter((_, imageIndex) => imageIndex !== index); setImages(next); onChange(next); };
  return <div className="product-image-field"><label>Product Images <small>({images.length}/{maximumImages})</small></label><label htmlFor={inputId} className="image-dropzone" onDragOver={(event) => event.preventDefault()} onDrop={drop}><UploadCloud size={28} /><strong>Drag and drop up to 3 product images here</strong><span>JPEG, PNG, or WebP — recommended 1200 × 1000px</span><span className="image-dropzone__browse"><ImagePlus size={15} /> Browse files</span><input id={inputId} type="file" accept="image/jpeg,image/png,image/webp" multiple onChange={change} hidden /></label>{images.length > 0 && <div className="image-upload-previews">{images.map((file, index) => <div className="image-upload-preview" key={`${file.name}-${index}`}><img src={URL.createObjectURL(file)} alt={`Product preview ${index + 1}`} /><button type="button" onClick={() => remove(index)} aria-label={`Remove image ${index + 1}`}><Trash2 size={17} /></button></div>)}</div>}</div>;
}
