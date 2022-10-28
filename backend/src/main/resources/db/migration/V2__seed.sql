--Password admin
INSERT INTO public.api_user
("admin", "password", url_photo, username, email, enabled)
VALUES(true, '$2a$04$ejcgfCN3ZT9ud.OyUUEhUuoGDY9qBMJPqSZTBRqJxghMk75L19By6', '', 'admin', 'admin@solinftec.com', true);

INSERT INTO public.api_user
("admin", "password", url_photo, username, email, enabled)
VALUES(false, '$2a$04$ejcgfCN3ZT9ud.OyUUEhUuoGDY9qBMJPqSZTBRqJxghMk75L19By6', '', 'matheusneves', 'matheus.neves@solinftec.com', true);

INSERT INTO public.api_user
("admin", "password", url_photo, username, email, enabled)
VALUES(false, '$2a$04$ejcgfCN3ZT9ud.OyUUEhUuoGDY9qBMJPqSZTBRqJxghMk75L19By6', '', 'jeansilva', 'jean.silva@solinftec.com', true);

INSERT INTO public.equipment
(description, color, latitude, longitude)
VALUES('Escavadeira John Deere', '#ff0000', -21.2207011, -50.4127031);

INSERT INTO public.equipment
(description, color, latitude, longitude)
VALUES('Trator Massey Ferguson', '#00FF00', -21.2284853, -50.4396062);

INSERT INTO public.equipment
(description, color, latitude, longitude)
VALUES('Caminhão Mercedes', '#0000FF', 40.4619749, -86.9296232);