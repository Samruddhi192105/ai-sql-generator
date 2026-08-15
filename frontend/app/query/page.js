"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { apiRequest } from "../../src/lib/api";

export default function QueryPage() {

  const router = useRouter();

  const [question, setQuestion] = useState("");

  const [data, setData] = useState(null);

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

    const [originalQuestion, setOriginalQuestion] = useState("");

  async function generateSQL(query = question) {

  if (typeof query !== "string" || !query.trim()) {
    setError("Please enter a question.");
    return;
  }

  setLoading(true);
  setError("");

  try {

    const result = await apiRequest(
      "/api/query/generate",
      {
        method: "POST",

        body: JSON.stringify({
          question: query
        })
      }
    );

    setData(result);

  } catch (error) {

    setError(error.message);

  } finally {

    setLoading(false);

  }
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

            <div>

              <h1 className="font-bold">
                AI SQL Generator
              </h1>

              <p className="text-[11px] text-zinc-500">
                Query workspace
              </p>

            </div>

          </button>

          <div className="flex items-center gap-1">

            <button
              onClick={() => router.push("/dashboard")}
              className="subtle-btn rounded-lg px-3 py-2 text-sm"
            >
              Dashboard
            </button>

            <button
              onClick={() => router.push("/history")}
              className="subtle-btn rounded-lg px-3 py-2 text-sm"
            >
              History
            </button>

          </div>

        </div>

      </nav>

      {/* Main */}

      <div className="page-content max-w-6xl mx-auto px-5 sm:px-8 py-10 sm:py-14">

        <div className="max-w-3xl">

          <p className="text-xs uppercase tracking-[.25em] text-zinc-500 mb-3">
            Query builder
          </p>

          <h2 className="text-4xl sm:text-5xl font-semibold tracking-tight">
            What do you want to know?
          </h2>

          <p className="text-zinc-400 mt-4 leading-7">
            Describe the result you need in everyday language. The backend
            will generate the SQL and return the result.
          </p>

        </div>

        {/* Query input */}

        <div className="glass-card rounded-3xl p-5 sm:p-7 mt-9">

          <label className="text-sm font-medium text-zinc-300">
            Your question
          </label>

          <textarea
            value={question}
            onChange={(e) =>
              setQuestion(e.target.value)
            }
            placeholder="e.g. Show employees earning more than 50000"
            className="soft-input w-full h-36 rounded-2xl p-5 mt-3 resize-none leading-7"
          />

          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mt-4">

            <p className="text-xs text-zinc-600">
              Tip: be specific about columns, filters, and sorting.
            </p>

            <button
              onClick={() => {
                setOriginalQuestion(question);
                generateSQL(question);
              }}
              disabled={loading}
              className="primary-btn px-6 py-3 rounded-xl"
            >
              {loading
                ? "Generating..."
                : "Generate SQL →"}
            </button>

          </div>

        </div>

        {/* Error */}

        {error && (

          <div className="status-error mt-6 rounded-2xl p-5">
            {error}
          </div>

        )}

        {/* Clarification

        {data &&
          data.status === "clarification_required" && (

            <div className="status-warn mt-7 rounded-2xl p-6">

              <p className="text-xs uppercase tracking-[.2em] text-zinc-500 mb-2">
                Need more detail
              </p>

              <h3 className="font-semibold text-xl">
                Clarification needed
              </h3>

              <p className="text-zinc-300 mt-3 leading-6">
                {data.clarificationQuestion ||
                  data.question}
              </p>

              <div className="mt-5 grid gap-2">

                {data.options?.map(
  (option, index) => {

    const optionText =
      typeof option === "string"
        ? option
        : option.label || option.value || String(option);

    return (
      <button
        key={index}
        onClick={() => {

  const optionText =
    typeof option === "string"
      ? option
      : option.label || option.value || String(option);

  const clarifiedQuestion =
    `${originalQuestion}. ${optionText}`;

  setQuestion(clarifiedQuestion);

  generateSQL(clarifiedQuestion);
}}
        className="text-left rounded-xl border border-white/10 bg-white/5 hover:bg-white/10 px-4 py-3 text-sm text-zinc-200 transition"
      >
        {optionText}
      </button>
    );
  }
)}

              </div>

            </div>

          )} */}

        {/* SQL */}

        {data && data.sql && (

          <section className="mt-8">

            <div className="flex items-center justify-between mb-3">

              <div>

                <p className="text-xs uppercase tracking-[.2em] text-zinc-600">
                  Generated output
                </p>

                <h3 className="text-xl font-semibold mt-1">
                  SQL query
                </h3>

              </div>

              <span className="text-xs text-zinc-500 font-mono">
                SQL
              </span>

            </div>

            <pre className="code-panel rounded-2xl p-6 overflow-x-auto text-sm leading-7 font-mono">
              {data.sql}
            </pre>

          </section>

        )}

        {/* Explanation */}

        {data && data.explanation && (

          <section className="glass-card rounded-2xl p-6 mt-6">

            <p className="text-xs uppercase tracking-[.2em] text-zinc-600">
              What it does
            </p>

            <h3 className="font-semibold text-lg mt-2">
              Explanation
            </h3>

            <p className="text-zinc-400 mt-3 leading-7">
              {data.explanation}
            </p>

          </section>

        )}

      </div>

    </main>
  );
}