"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function DashboardPage() {
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      router.push("/login");
    }
  }, [router]);

  function handleLogout() {
    localStorage.removeItem("token");
    router.push("/login");
  }

  return (
    <main className="app-shell min-h-screen">

      {/* Navigation */}

      <nav className="glass-nav">

        <div className="page-content max-w-6xl mx-auto px-5 sm:px-8 py-4 flex items-center justify-between">

          <button
            onClick={() => router.push("/dashboard")}
            className="flex items-center gap-3"
          >

            <span className="h-9 w-9 rounded-xl bg-white text-black flex items-center justify-center font-black text-sm">
              SQL
            </span>

            <div className="text-left">

              <h1 className="font-bold tracking-tight">
                AI SQL Generator
              </h1>

              <p className="text-[11px] text-zinc-500">
                Natural language → SQL
              </p>

            </div>

          </button>

          <button
            onClick={handleLogout}
            className="subtle-btn rounded-lg px-3 py-2 text-sm"
          >
            Logout
          </button>

        </div>

      </nav>

      {/* Main content */}

      <div className="page-content max-w-6xl mx-auto px-5 sm:px-8 py-14 sm:py-20">

        <div className="max-w-3xl">

          <p className="text-xs uppercase tracking-[.25em] text-zinc-500 mb-4">
            Workspace
          </p>

          <h2 className="text-4xl sm:text-5xl font-semibold tracking-tight">

            Ask your database

            <span className="block text-zinc-500">
              without writing SQL.
            </span>

          </h2>

          <p className="mt-5 text-zinc-400 text-base sm:text-lg leading-7 max-w-2xl">
            Turn everyday questions into SQL, inspect the generated query,
            and see the results in one focused workspace.
          </p>

        </div>

        {/* Cards */}

        <div className="grid md:grid-cols-2 gap-5 mt-12">

          {/* Generate SQL */}

          <button
            onClick={() => router.push("/query")}
            className="glass-card glass-card-hover rounded-3xl p-7 sm:p-9 text-left group"
          >

            <div className="flex items-center justify-between">

              <div className="h-12 w-12 rounded-2xl bg-white text-black flex items-center justify-center font-black">
                →
              </div>

              <span className="text-xs text-zinc-500">
                01
              </span>

            </div>

            <h3 className="text-2xl font-semibold mt-10">
              Generate SQL
            </h3>

            <p className="text-zinc-400 mt-3 leading-6">
              Describe what you need in plain language and let the generator
              build the query.
            </p>

            <span className="inline-block mt-8 text-sm text-zinc-200 group-hover:translate-x-1 transition-transform">
              Start a query →
            </span>

          </button>

          {/* History */}

          <button
            onClick={() => router.push("/history")}
            className="glass-card glass-card-hover rounded-3xl p-7 sm:p-9 text-left group"
          >

            <div className="flex items-center justify-between">

              <div className="h-12 w-12 rounded-2xl border border-white/15 bg-white/5 flex items-center justify-center text-white">
                ↺
              </div>

              <span className="text-xs text-zinc-500">
                02
              </span>

            </div>

            <h3 className="text-2xl font-semibold mt-10">
              Query History
            </h3>

            <p className="text-zinc-400 mt-3 leading-6">
              Revisit previously generated queries and quickly inspect their
              SQL and execution status.
            </p>

            <span className="inline-block mt-8 text-sm text-zinc-200 group-hover:translate-x-1 transition-transform">
              View history →
            </span>

          </button>

        </div>

      </div>

    </main>
  );
}