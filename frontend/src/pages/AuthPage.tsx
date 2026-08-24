import { type FormEvent, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Logo } from '../components/layout/Logo';
import { Button } from '../components/ui/Button';
import { useAuth } from '../features/auth/AuthContext';

export function AuthPage({ mode }: { mode?: 'login' | 'register' }) {
  const auth = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  // Login state
  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [loginError, setLoginError] = useState('');
  const [loginSaving, setLoginSaving] = useState(false);

  // Register state
  const [registerEmail, setRegisterEmail] = useState('');
  const [registerPassword, setRegisterPassword] = useState('');
  const [registerError, setRegisterError] = useState('');
  const [registerSaving, setRegisterSaving] = useState(false);

  const redirectPath = (location.state as { from?: string } | null)?.from ?? '/';

  async function handleLogin(e: FormEvent) {
    e.preventDefault();
    setLoginError('');
    setLoginSaving(true);
    try {
      await auth.login(loginEmail, loginPassword);
      navigate(redirectPath);
    } catch (err) {
      setLoginError(err instanceof Error ? err.message : 'Unable to sign in.');
    } finally {
      setLoginSaving(false);
    }
  }

  async function handleRegister(e: FormEvent) {
    e.preventDefault();
    setRegisterError('');
    setRegisterSaving(true);
    try {
      await auth.register(registerEmail, registerPassword);
      navigate(redirectPath);
    } catch (err) {
      setRegisterError(err instanceof Error ? err.message : 'Unable to create account.');
    } finally {
      setRegisterSaving(false);
    }
  }

  return (
    <div className="auth-page-container">
      <header className="auth-header">
        <Logo />
      </header>
      <main className="auth-split-layout">
        {/* Sign In Card */}
        <section className={`auth-card ${mode === 'login' ? 'active-mode' : ''}`}>
          <p className="eyebrow">WELCOME BACK</p>
          <h1>Sign in to Shoply</h1>
          <p className="auth-intro">Access your orders and continue shopping.</p>
          <form onSubmit={handleLogin}>
            <label>
              Email address
              <input
                value={loginEmail}
                onChange={(e) => setLoginEmail(e.target.value)}
                required
                type="email"
                autoComplete="email"
                placeholder="you@example.com"
              />
            </label>
            <label>
              Password
              <input
                value={loginPassword}
                onChange={(e) => setLoginPassword(e.target.value)}
                required
                type="password"
                autoComplete="current-password"
                placeholder="••••••••"
              />
            </label>
            {loginError && <p className="form-error">{loginError}</p>}
            <Button type="submit" className="auth-submit" disabled={loginSaving}>
              {loginSaving ? 'Signing in…' : 'Sign in'}
            </Button>
          </form>
        </section>

        {/* Register Card */}
        <section className={`auth-card ${mode === 'register' ? 'active-mode' : ''}`}>
          <p className="eyebrow">WELCOME TO SHOPLY</p>
          <h1>Create your account</h1>
          <p className="auth-intro">Save your orders, manage your profile, and check out faster.</p>
          <form onSubmit={handleRegister}>
            <label>
              Email address
              <input
                value={registerEmail}
                onChange={(e) => setRegisterEmail(e.target.value)}
                required
                type="email"
                autoComplete="email"
                placeholder="you@example.com"
              />
            </label>
            <label>
              Password
              <input
                value={registerPassword}
                onChange={(e) => setRegisterPassword(e.target.value)}
                required
                minLength={8}
                type="password"
                autoComplete="new-password"
                placeholder="••••••••"
              />
            </label>
            {registerError && <p className="form-error">{registerError}</p>}
            <Button type="submit" className="auth-submit" disabled={registerSaving}>
              {registerSaving ? 'Creating…' : 'Create account'}
            </Button>
          </form>
        </section>
      </main>
    </div>
  );
}
