const rawBase = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api';
export const API_BASE = rawBase.endsWith('/api') ? rawBase : `${rawBase.replace(/\/$/, '')}/api`;

export async function get<T>(path:string):Promise<T>{const r=await fetch(`${API_BASE}${path}`,{cache:'no-store'});if(!r.ok)throw new Error(`Request failed: ${r.status}`);return r.json() as Promise<T>}
export async function authenticatedGet<T>(path:string,token:string):Promise<T>{const r=await fetch(`${API_BASE}${path}`,{headers:{Authorization:`Bearer ${token}`}});if(!r.ok)throw new Error(`Request failed: ${r.status}`);return r.json() as Promise<T>}
export async function authenticatedWrite<T>(path:string,token:string,method:'POST'|'PUT'|'DELETE',body?:unknown):Promise<T>{const r=await fetch(`${API_BASE}${path}`,{method,headers:{Authorization:`Bearer ${token}`,'Content-Type':'application/json'},body:body===undefined?undefined:JSON.stringify(body)});if(!r.ok)throw new Error(`Request failed: ${r.status}`);return r.json() as Promise<T>}

