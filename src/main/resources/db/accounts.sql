
CREATE TABLE public.accounts
(
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    hascovid character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT accounts_username_key UNIQUE (username)
)