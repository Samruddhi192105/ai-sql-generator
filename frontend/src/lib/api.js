const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL;

export async function apiRequest(endpoint, options = {}) {
    const token = localStorage.getItem("token");

    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...(token && {
                Authorization: `Bearer ${token}`,
            }),
            ...options.headers,
        },
    });

    const text = await response.text();

    console.log("STATUS:", response.status);
    console.log("RESPONSE:", text);

    let data = {};

    try {
        data = text ? JSON.parse(text) : {};
    } catch (error) {
        throw new Error(`Server returned invalid JSON: ${text}`);
    }

    if (!response.ok) {
        throw new Error(data.message || "Something went wrong");
    }

    return data;
}