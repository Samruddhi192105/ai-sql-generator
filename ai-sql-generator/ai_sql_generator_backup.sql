--
-- PostgreSQL database dump
--

\restrict AxvVqbJwHdP0mWZxQbTjGTaJ6TCIWguu0I5efpIpA7XJ1t5RiYfuqQuis8v94jr

-- Dumped from database version 18.4
-- Dumped by pg_dump version 18.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: departments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.departments (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    location character varying(100)
);


ALTER TABLE public.departments OWNER TO postgres;

--
-- Name: departments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.departments_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.departments_id_seq OWNER TO postgres;

--
-- Name: departments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.departments_id_seq OWNED BY public.departments.id;


--
-- Name: projects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.projects (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    department_id integer,
    budget numeric(12,2)
);


ALTER TABLE public.projects OWNER TO postgres;

--
-- Name: projects_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.projects_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.projects_id_seq OWNER TO postgres;

--
-- Name: projects_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.projects_id_seq OWNED BY public.projects.id;


--
-- Name: query_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.query_history (
    id bigint NOT NULL,
    natural_language_query text NOT NULL,
    generated_sql text,
    execution_status character varying(20) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    user_id bigint
);


ALTER TABLE public.query_history OWNER TO postgres;

--
-- Name: query_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.query_history_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.query_history_id_seq OWNER TO postgres;

--
-- Name: query_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.query_history_id_seq OWNED BY public.query_history.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(100) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: departments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments ALTER COLUMN id SET DEFAULT nextval('public.departments_id_seq'::regclass);


--
-- Name: projects id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects ALTER COLUMN id SET DEFAULT nextval('public.projects_id_seq'::regclass);


--
-- Name: query_history id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.query_history ALTER COLUMN id SET DEFAULT nextval('public.query_history_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.departments (id, name, location) FROM stdin;
1	Engineering	Pune
2	Human Resources	Mumbai
3	Finance	Bangalore
4	Marketing	Delhi
5	Sales	Hyderabad
\.


--
-- Data for Name: projects; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.projects (id, name, department_id, budget) FROM stdin;
1	AI SQL Generator	1	500000.00
2	Employee Portal	1	300000.00
3	Recruitment System	2	200000.00
4	Financial Dashboard	3	450000.00
5	Marketing Campaign	4	150000.00
6	Sales Analytics	5	350000.00
\.


--
-- Data for Name: query_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.query_history (id, natural_language_query, generated_sql, execution_status, created_at, user_id) FROM stdin;
1	Employees earning more than 50000 in the company not department	SELECT e.name, e.salary\nFROM employees e\nWHERE e.salary > 50000 AND e.department_id NOT IN (\n    SELECT dp.id\n    FROM departments dp\n);	SUCCESS	2026-08-09 20:03:18.343843	\N
2	total Salary of all employees	SELECT SUM(salary) AS total_salary FROM employees;	SUCCESS	2026-08-09 20:08:03.856862	\N
3	Max salary among all employees in all departments	SELECT MAX(salary) FROM employees;	SUCCESS	2026-08-09 21:40:10.94835	2
4	highest Global salary of all departments	SELECT MAX(salary) FROM employees;	SUCCESS	2026-08-09 22:04:40.789427	3
5	employee earning more than 40000 from all the departments 	SELECT e.id, e.name, e.salary\nFROM employees e\nJOIN departments d ON e.department_id = d.id\nWHERE e.salary > 40000;	SUCCESS	2026-08-09 22:46:36.049441	5
6	Show all employees earning less than 50000	SELECT name, salary \nFROM employees \nWHERE salary < 50000;	SUCCESS	2026-08-09 23:05:10.849339	5
7	Show all employees earning less than 40,000 strictly	SELECT * FROM employees WHERE salary < 40000;	SUCCESS	2026-08-09 23:18:47.436036	5
9	show employees earning more than 40000	SELECT name, salary\nFROM employees\nWHERE salary > 40000;	SUCCESS	2026-08-15 12:47:43.476157	6
10	Age 50 and above	SELECT e.*\nFROM employees e\nWHERE e.salary >= 50000;	SUCCESS	2026-08-15 12:58:42.770609	6
11	show employees earning more than 50000	SELECT name, salary\nFROM employees\nWHERE salary > 50000;	SUCCESS	2026-08-15 13:14:07.824886	6
12	show students having age more than 20. Age of students	SELECT name FROM students WHERE age > 20;	SUCCESS	2026-08-15 14:57:55.211355	6
13	drop table students\n\n. Department A	SELECT *\nFROM departments\nWHERE name = 'Department A';	SUCCESS	2026-08-15 14:59:08.5072	6
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, email, password, created_at) FROM stdin;
2	samruddhi	samruddhi@example.com	$2a$10$IJHbkT0JJDewzRe4hJqm6ekXSNdRYwBwZe/WW.Ypglt0Z1h7i04hy	2026-08-09 21:31:29.745178
3	ram	ram@gmail.com	$2a$10$KnqLX38jIxF7UCxKcMv3f..FvvRLDyf7bSKFSe1knEd5xMoUttp8a	2026-08-09 21:44:00.454829
5	testuser	test@example.com	$2a$10$bfmB423Q7fEOGciKAGXyLe2r4jq0WWlT8s5WHKtuZL6ndCLif1sNG	2026-08-09 22:10:18.14683
6	Samruddhi	ghawadesamruddhi19@gmail.com	$2a$10$dazUKNMWF2g2F19z8IE34eLHLmxM8kVNhBYOzEH0z8QVYu0MJC5Tm	2026-08-09 23:55:25.185969
\.


--
-- Name: departments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.departments_id_seq', 5, true);


--
-- Name: projects_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.projects_id_seq', 6, true);


--
-- Name: query_history_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.query_history_id_seq', 13, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- Name: departments departments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);


--
-- Name: projects projects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_pkey PRIMARY KEY (id);


--
-- Name: query_history query_history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.query_history
    ADD CONSTRAINT query_history_pkey PRIMARY KEY (id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: query_history fk_query_history_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.query_history
    ADD CONSTRAINT fk_query_history_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: projects projects_department_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projects
    ADD CONSTRAINT projects_department_id_fkey FOREIGN KEY (department_id) REFERENCES public.departments(id);


--
-- PostgreSQL database dump complete
--

\unrestrict AxvVqbJwHdP0mWZxQbTjGTaJ6TCIWguu0I5efpIpA7XJ1t5RiYfuqQuis8v94jr

