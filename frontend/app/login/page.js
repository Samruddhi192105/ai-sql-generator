"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { apiRequest } from "@/src/lib/api";

export default function LoginPage() {

  const router = useRouter();

  const [email, setEmail] = useState("");

  const [password, setPassword] = useState("");

  const [error, setError] = useState("");

  const [loading, setLoading] = useState(false);

  async function handleLogin(e) {

    e.preventDefault();

    setError("");

    setLoading(true);

    try {

      const data = await apiRequest(
        "/api/auth/login",
        {
          method: "POST",

          body: JSON.stringify({
            email,
            password
          })
        }
      );

      localStorage.setItem(
        "token",
        data.token
      );

      router.push("/dashboard");

    } catch (error) {

      setError(error.message);

    } finally {

      setLoading(false);

    }

  }

  return (
    <main className="app-shell min-h-screen flex items-center justify-center px-5 py-10">

      <div className="page-content w-full max-w-5xl grid lg:grid-cols-[1.05fr_.95fr] gap-8 items-center">

        {/* Left section */}

        <section className="hidden lg:block px-8">

          <div className="h-12 w-12 rounded-2xl bg-white text-black flex items-center justify-center font-black mb-8">
            SQL
          </div>

          <p className="text-xs uppercase tracking-[.3em] text-zinc-500">
            AI SQL Generator
          </p>

          <h1 className="text-5xl font-semibold tracking-tight mt-4 leading-[1.05]">

            Query your data.

            <span className="block text-zinc-500">
              Speak naturally.
            </span>

          </h1>

          <p className="text-zinc-400 mt-6 max-w-md leading-7">
            Generate database queries from plain English and understand
            exactly what the SQL is doing.
          </p>

        </section>

        {/* Login card */}

        <div className="glass-card rounded-3xl p-7 sm:p-9">

          <div className="mb-8">

            <p className="text-xs uppercase tracking-[.2em] text-zinc-500">
              Welcome back
            </p>

            <h2 className="text-3xl font-semibold mt-2">
              Sign in
            </h2>

            <p className="text-zinc-500 mt-2 text-sm">
              Continue to your SQL workspace.
            </p>

          </div>

          {error && (

            <div className="status-error rounded-xl p-4 mb-5 text-sm">
              {error}
            </div>

          )}

          <form
            onSubmit={handleLogin}
            className="space-y-4"
          >

            <div>

              <label className="text-sm text-zinc-400 block mb-2">
                Email
              </label>

              <input
                type="email"
                placeholder="you@example.com"
                value={email}
                onChange={(e) =>
                  setEmail(e.target.value)
                }
                className="soft-input w-full p-3.5 rounded-xl"
                required
              />

            </div>

            <div>

              <label className="text-sm text-zinc-400 block mb-2">
                Password
              </label>

              <input
                type="password"
                placeholder="Enter your password"
                value={password}
                onChange={(e) =>
                  setPassword(e.target.value)
                }
                className="soft-input w-full p-3.5 rounded-xl"
                required
              />

            </div>

            <button
              type="submit"
              disabled={loading}
              className="primary-btn w-full p-3.5 rounded-xl mt-2"
            >
              {loading
                ? "Logging in..."
                : "Login →"}
            </button>

          </form>

          <div className="border-t border-white/10 mt-7 pt-6 text-center">

            <span className="text-sm text-zinc-500">
              Don't have an account?{" "}
            </span>

            <button
              onClick={() => router.push("/register")}
              className="text-sm text-white hover:underline"
            >
              Create one
            </button>

          </div>

        </div>

      </div>

    </main>
  );
}