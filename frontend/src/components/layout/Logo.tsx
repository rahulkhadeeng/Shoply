import { Link } from 'react-router-dom';

export function Logo() {
  return (
    <Link className="logo" to="/">
      <img
        src="/logo.png"
        alt="Shoply Logo"
        style={{
          width: '32px',
          height: '32px',
          objectFit: 'contain',
          borderRadius: '6px'
        }}
      />
      Shoply
    </Link>
  );
}
