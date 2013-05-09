-- we dont' describe our tables explicitly, play handles that w/ the models..
-- woot

GRANT USAGE ON *.* TO 'playuser'@'localhost';
DROP USER 'playuser'@'localhost';

CREATE USER 'playuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'playuser'@'localhost';

FLUSH PRIVILEGES;

