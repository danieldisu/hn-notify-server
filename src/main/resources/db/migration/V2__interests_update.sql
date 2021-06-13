-- noinspection SqlWithoutWhere
DELETE
FROM interest;

ALTER TABLE interest
    ADD COLUMN interest_keywords TEXT;

INSERT INTO interest (id, user_id, interest_name, interest_keywords, added_on)
VALUES (1, 1, 'android', 'android', CURRENT_DATE);

INSERT INTO interest (id, user_id, interest_name, interest_keywords, added_on)
VALUES (2, 1, 'compose', 'compose', CURRENT_DATE);

INSERT INTO interest (id, user_id, interest_name, interest_keywords, added_on)
VALUES (3, 1, 'kotlin', 'kotlin', CURRENT_DATE);

INSERT INTO interest (id, user_id, interest_name, interest_keywords, added_on)
VALUES (4, 1, 'spain', 'spain', CURRENT_DATE);

INSERT INTO interest (id, user_id, interest_name, interest_keywords, added_on)
VALUES (5, 1, 'madrid', 'madrid', CURRENT_DATE);
