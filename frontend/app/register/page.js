"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { apiRequest } from "../../src/lib/api";

export default function RegisterPage() {

  const router = useRouter();

  const [username, setUsername] = useState("");

  const [email, setEmail] = useState("");

  const [password, setPassword] = useState("");

  const [error, setError] = useState("");

  const [loading, setLoading] = useState(false);

  async function handleRegister(e) {

    e.preventDefault();

    setError("");

    setLoading(true);

    try {

      await apiRequest(
        "/api/auth/register",
        {
          method: "POST",

          body: JSON.stringify({
            username,
            email,
            password
          })
        }
      );

      router.push("/login");

    } catch (error) {

      setError(error.message);

    } finally {

      setLoading(false);

    }

  }

  return (
    <main className="app-shell min-h-screen flex items-center justify-center px-5 py-10">

      <div className="page-content w-full max-w-5xl grid lg:grid-cols-[1.05fr_.95fr] gap-8 items-center">

        {/* Left */}

        <section className="hidden lg:block px-8">

          <div className="h-12 w-12 rounded-2xl border border-white/15 bg-white/5 flex items-center justify-center font-black mb-8">
            SQL
          </div>

          <p className="text-xs uppercase tracking-[.3em] text-zinc-500">
            Get started
          </p>

          <h1 className="text-5xl font-semibold tracking-tight mt-4 leading-[1.05]">

            Build queries

            <span className="block text-zinc-500">
              the natural way.
            </span>

          </h1>

          <p className="text-zinc-400 mt-6 max-w-md leading-7">
            Create your workspace and turn natural-language questions
            into useful SQL.
          </p>

        </section>

        {/* Register card */}

        <div className="glass-card rounded-3xl p-7 sm:p-9">

          <div className="mb-8">

            <p className="text-xs uppercase tracking-[.2em] text-zinc-500">
              New workspace
            </p>

            <h2 className="text-3xl font-semibold mt-2">
              Create account
            </h2>

            <p className="text-zinc-500 mt-2 text-sm">
              Set up your account to start generating queries.
            </p>

          </div>

          {error && (

            <div className="status-error rounded-xl p-4 mb-5 text-sm">
              {error}
            </div>

          )}

          <form
            onSubmit={handleRegister}
            className="space-y-4"
          >

            {/* Username */}

            <div>

              <label className="text-sm text-zinc-400 block mb-2">
                Username
              </label>

              <input
                type="text"
                placeholder="Your username"
                value={username}
                onChange={(e) =>
                  setUsername(e.target.value)
                }
                className="soft-input w-full p-3.5 rounded-xl"
                required
              />

            </div>

            {/* Email */}

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

            {/* Password */}

            <div>

              <label className="text-sm text-zinc-400 block mb-2">
                Password
              </label>

              <input
                type="password"
                placeholder="Create a password"
                value={password}
                onChange={(e) =>
                  setPassword(e.target.value)
                }
                className="soft-input w-full p-3.5 rounded-xl"
                required
              />

            </div>

            {/* Submit */}

            <button
              type="submit"
              disabled={loading}
              className="primary-btn w-full p-3.5 rounded-xl mt-2"
            >
              {loading
                ? "Creating account..."
                : "Create account →"}
            </button>

          </form>

          <div className="border-t border-white/10 mt-7 pt-6 text-center">

            <span className="text-sm text-zinc-500">
              Already have an account?{" "}
            </span>

            <button
              onClick={() => router.push("/login")}
              className="text-sm text-white hover:underline"
            >
              Login
            </button>

          </div>

        </div>

      </div>

    </main>
  );
}