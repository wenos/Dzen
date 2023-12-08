INSERT into c_security_type(id, name, title, created_at, updated_at)
VALUES (nextval('security_type_id_seq'), 'common_share', 'Акция обыкновенная', now(), now()),
       (nextval('security_type_id_seq'), 'preferred_share', 'Акция привилегированная', now(), now()),
       (nextval('security_type_id_seq'), 'currency', 'Валюта', now(), now()),
--        (nextval('security_type_id_seq'), 'futures', 'Фьючерс', now(), now()),
       (nextval('security_type_id_seq'), 'ofz_bond', 'Государственная облигация', now(), now());



