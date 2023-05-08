CREATE TABLE IF NOT EXISTS image (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100) NOT NULL,
    description varchar(255),
    size long,
    original_file_name varchar(100),
    content_type varchar(50),
    image_payload longtext,
    created_date datetime,
    updated_date datetime
);