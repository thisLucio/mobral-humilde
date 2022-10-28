CREATE TABLE public.api_user (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	"admin" bool NOT NULL,
	"password" varchar(255) NULL,
	url_photo varchar(255) NULL,
	username varchar(255) NULL,
	email varchar(255) NULL,
	enabled bool null,
	UNIQUE(username),
	UNIQUE(email),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE public.equipment (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	description varchar(255) NULL,
	color varchar(7) NULL,
	latitude decimal(23, 15) NOT NULL,
	longitude decimal(23, 15) NOT NULL,
	CONSTRAINT equipment_pkey PRIMARY KEY (id)
);

CREATE TABLE public.messages (
	id bigserial PRIMARY KEY,
	content text NOT NULL,
	"timestamp" TIMESTAMP with time zone NOT NULL,
	user_id int8 NOT NULL references api_user(id) ON DELETE CASCADE,
	equipment_id bigint NOT NULL references equipment(id) ON DELETE CASCADE,
	sender varchar(20) NOT NULL
);


