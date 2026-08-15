"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (token) {
      router.push("/dashboard");
    } else {
      router.push("/login");
    }
  }, [router]);

  return (
    <main className="app-shell min-h-screen flex items-center justify-center">
      <div className="page-content text-center">

        <div className="mx-auto mb-5 h-10 w-10 rounded-full border border-white/20 border-t-white animate-spin" />

        <p className="text-sm text-zinc-400 tracking-wide">
          Loading AI SQL Generator...
        </p>

      </div>
    </main>
  );
}