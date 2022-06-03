CREATE TABLE sec_role (
  id UUID NOT NULL,
  create_ts TIMESTAMP WITHOUT TIME ZONE,
  update_ts TIMESTAMP WITHOUT TIME ZONE,
  name VARCHAR(255) NOT NULL,
  loc_name VARCHAR(255),
  description VARCHAR(1000),
  is_default_role BOOLEAN,
  CONSTRAINT pk_sec_role PRIMARY KEY (id)
);

CREATE TABLE sec_user (
  id UUID NOT NULL,
  create_ts TIMESTAMP WITHOUT TIME ZONE,
  update_ts TIMESTAMP WITHOUT TIME ZONE,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(255),
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  middle_name VARCHAR(255),
  email VARCHAR(255),
  language VARCHAR(20),
  time_zone VARCHAR(255),
  active BOOLEAN,
  CONSTRAINT pk_sec_user PRIMARY KEY (id)
);

CREATE TABLE sec_user_role (
  role_id UUID NOT NULL,
  user_id UUID NOT NULL,
  CONSTRAINT pk_sec_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE sec_role ADD CONSTRAINT uc_sec_role_name UNIQUE (name);

ALTER TABLE sec_user_role ADD CONSTRAINT fk_secuserol_on_role FOREIGN KEY (role_id) REFERENCES sec_role (id);

ALTER TABLE sec_user_role ADD CONSTRAINT fk_secuserol_on_user FOREIGN KEY (user_id) REFERENCES sec_user (id);