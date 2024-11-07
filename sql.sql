CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tb_files  (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    file TEXT,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_files ADD CONSTRAINT file_pk PRIMARY KEY (id);

CREATE TABLE tb_roles (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_roles ADD CONSTRAINT role_pk PRIMARY KEY (id);
ALTER TABLE tb_roles ADD CONSTRAINT role_bk UNIQUE (code);

CREATE TABLE tb_employment_type (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_employment_type ADD CONSTRAINT employment_type_pk PRIMARY KEY (id);
ALTER TABLE tb_employment_type ADD CONSTRAINT employment_type_bk UNIQUE (code);

CREATE TABLE tb_level_experiences (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_level_experiences ADD CONSTRAINT level_experience_pk PRIMARY KEY (id);
ALTER TABLE tb_level_experiences ADD CONSTRAINT level_experience_bk UNIQUE (code);

CREATE TABLE tb_job_statuses (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(50) NOT NULL,
    status VARCHAR(100) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_job_statuses ADD CONSTRAINT job_status_pk PRIMARY KEY (id);
ALTER TABLE tb_job_statuses ADD CONSTRAINT job_status_bk UNIQUE (code);

CREATE TABLE tb_selection_stages(
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_selection_stages ADD CONSTRAINT selection_stage_pk PRIMARY KEY (id);
ALTER TABLE tb_selection_stages ADD CONSTRAINT selection_stage_bk UNIQUE (code);

CREATE TABLE tb_selection_statuses (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_selection_statuses ADD CONSTRAINT selection_status_pk PRIMARY KEY (id);
ALTER TABLE tb_selection_statuses ADD CONSTRAINT selection_status_bk UNIQUE (code);

CREATE TABLE tb_locations (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    name VARCHAR(150) NOT NULL,
    code VARCHAR(50) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_locations ADD CONSTRAINT location_pk PRIMARY KEY (id);
ALTER TABLE tb_locations ADD CONSTRAINT location_bk UNIQUE (code);

CREATE TABLE tb_users(
    id text DEFAULT uuid_generate_v4() NOT NULL,
    role_id text NOT NULL ,
    username VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_users ADD CONSTRAINT user_pk PRIMARY KEY (id);
ALTER TABLE tb_users ADD CONSTRAINT user_username_bk UNIQUE (username);
ALTER TABLE tb_users ADD CONSTRAINT user_email_bk UNIQUE (email);
ALTER TABLE tb_users ADD CONSTRAINT user_role_fk FOREIGN KEY (role_id) REFERENCES tb_roles;

CREATE TYPE gender_type AS ENUM ('L', 'P');

CREATE TABLE tb_profile_users (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    user_id text NOT NULL,
    image_file_id TEXT,
    cv_file_id TEXT,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    city VARCHAR(255) NOT NULL,
    gender gender_type NOT NULL,
    born DATE NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_profile_users ADD CONSTRAINT profile_user_pk PRIMARY KEY (id);
ALTER TABLE tb_profile_users ADD CONSTRAINT profile_user_phone_bk UNIQUE (phone);
ALTER TABLE tb_profile_users ADD CONSTRAINT profile_user_fk_user FOREIGN KEY (user_id) REFERENCES tb_users;
ALTER TABLE tb_profile_users ADD CONSTRAINT profile_user_fk_file_cv FOREIGN KEY (cv_file_id) REFERENCES tb_files;
ALTER TABLE tb_profile_users ADD CONSTRAINT profile_user_fk_file_image FOREIGN KEY (image_file_id) REFERENCES tb_files;
ALTER TABLE tb_profile_users ALTER COLUMN gender TYPE VARCHAR(1);


CREATE TABLE tb_user_educations (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    user_id TEXT NOT NULL,
    univ_name VARCHAR(255) NOT NULL,
    major VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_user_educations ADD CONSTRAINT user_education_pk PRIMARY KEY(id);
ALTER TABLE tb_user_educations ADD CONSTRAINT user_education_fk FOREIGN KEY (user_id) REFERENCES tb_users;
ALTER TABLE tb_user_educations ADD CONSTRAINT user_education_bk UNIQUE (user_id);

CREATE TABLE tb_user_experiences (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    user_id text NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    position VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_user_experiences ADD CONSTRAINT user_experience_pk PRIMARY KEY (id);
ALTER TABLE tb_user_experiences ADD CONSTRAINT user_experience_fk_user FOREIGN KEY (user_id) REFERENCES tb_users;

CREATE TABLE tb_user_certifications (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    user_id TEXT NOT NULL,
    certif_file_id TEXT NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_user_certifications ADD CONSTRAINT user_certification_pk PRIMARY KEY (id);
ALTER TABLE tb_user_certifications ADD CONSTRAINT user_certification_fk FOREIGN KEY (user_id) REFERENCES tb_users;
ALTER TABLE tb_user_certifications ADD CONSTRAINT user_certification_bk UNIQUE (user_id);

CREATE TABLE tb_title_jobs (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    title VARCHAR(255) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_title_jobs ADD CONSTRAINT title_job_pk PRIMARY KEY(id);

CREATE TABLE tb_job_vacancies (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    title_job_id TEXT NOT NULL,
    employment_type_id TEXT NOT NULL,
    level_experience_id TEXT NOT NULL,
    location_id TEXT NOT NULL,
    overview TEXT NOT NULL,
    start_salary BIGINT NOT NULL,
    end_salary BIGINT NOT NULL,
    deadline_apply DATE NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_job_vacancies ADD CONSTRAINT job_pk PRIMARY KEY (id);
ALTER TABLE tb_job_vacancies ADD CONSTRAINT job_fk_title FOREIGN KEY (title_job_id) REFERENCES tb_title_jobs;
ALTER TABLE tb_job_vacancies ADD CONSTRAINT job_fk_employment_type FOREIGN KEY (employment_type_id) REFERENCES tb_employment_type;
ALTER TABLE tb_job_vacancies ADD CONSTRAINT job_fk_level_experience FOREIGN KEY (level_experience_id) REFERENCES tb_level_experiences;
ALTER TABLE tb_job_vacancies ADD CONSTRAINT job_fk_location FOREIGN KEY (location_id) REFERENCES tb_locations;

CREATE TABLE tb_job_vacancy_detail (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    vacancy_id TEXT NOT NULL,
    pic_user_id TEXT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_job_vacancy_detail ADD CONSTRAINT job_vacancy_detail_pk PRIMARY KEY (id);
ALTER TABLE tb_job_vacancy_detail ADD CONSTRAINT job_vacancy_detail_fk_vacancy FOREIGN KEY (vacancy_id) REFERENCES tb_job_vacancies;
ALTER TABLE tb_job_vacancy_detail ADD CONSTRAINT job_vacancy_detail_fk_pic_user_id FOREIGN KEY (pic_user_id) REFERENCES tb_users;


CREATE TABLE tb_job_descriptions (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    title_job_id TEXT NOT NULL,
    description TEXT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_job_descriptions ADD CONSTRAINT job_description_pk PRIMARY KEY (id);
ALTER TABLE tb_job_descriptions ADD CONSTRAINT job_description_fk_title_job FOREIGN KEY (title_job_id) REFERENCES tb_title_jobs;

CREATE TABLE tb_job_spesifications (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    title_job_id TEXT NOT NULL,
    spesification TEXT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_job_spesifications ADD CONSTRAINT job_spesification_pk PRIMARY KEY (id);
ALTER TABLE tb_job_spesifications ADD CONSTRAINT job_spesification_fk_job_title FOREIGN KEY (title_job_id) REFERENCES tb_title_jobs;

CREATE TABLE tb_job_vacancies_transactions (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    job_vacancy_id TEXT NOT NULL,
    job_status_id TEXT NOT NULL,
    user_id TEXT NOT NULL,
    date DATE NOT NULL,
    number INT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_job_vacancies_transactions ADD CONSTRAINT job_transaction_pk PRIMARY KEY (id);
ALTER TABLE tb_job_vacancies_transactions ADD CONSTRAINT job_transaction_fk_job FOREIGN KEY (job_vacancy_id) REFERENCES tb_job_vacancies;
ALTER TABLE tb_job_vacancies_transactions ADD CONSTRAINT job_transaction_fk_job_status FOREIGN KEY (job_status_id) REFERENCES tb_job_statuses;
ALTER TABLE tb_job_vacancies_transactions ADD CONSTRAINT job_transaction_fk_user FOREIGN KEY (user_id) REFERENCES tb_users;

CREATE TABLE tb_apply_candidates (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    user_id TEXT NOT NULL,
    vacancy_job_id TEXT NOT NULL,
    date_apply DATE NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_apply_candidates ADD CONSTRAINT apply_candidate_pk PRIMARY KEY (id);
ALTER TABLE tb_apply_candidates ADD CONSTRAINT apply_candidate_fk_user FOREIGN KEY (user_id) REFERENCES tb_users;
ALTER TABLE tb_apply_candidates ADD CONSTRAINT apply_candidate_fk_job FOREIGN KEY (vacancy_job_id) REFERENCES tb_job_vacancies;

CREATE TABLE tb_apply_attachments (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    apply_candidate_id TEXT NOT NULL,
    file_attachment_id TEXT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_apply_attachments ADD CONSTRAINT apply_attachment_pk PRIMARY KEY (id);
ALTER TABLE tb_apply_attachments ADD CONSTRAINT apply_attachment_fk_apply_candidate FOREIGN KEY (apply_candidate_id) REFERENCES tb_apply_candidates;
ALTER TABLE tb_apply_attachments ADD CONSTRAINT apply_attachment_fk_file FOREIGN KEY (file_attachment_id) REFERENCES tb_files;

CREATE TABLE tb_stage_process (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    candidate_user_id TEXT NOT NULL,
    vacancy_job_id TEXT NOT NULL,
    stage_selection_id TEXT NOT NULL,
    selection_status_id TEXT NOT NULL,
    score INT DEFAULT 0,
    date_deadline TIMESTAMPTZ NOT NULL,
    number INT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_stage_process ADD CONSTRAINT stage_proces_pk PRIMARY KEY (id);
ALTER TABLE tb_stage_process ADD CONSTRAINT stage_proces_fk_user FOREIGN KEY (candidate_user_id) REFERENCES tb_users;
ALTER TABLE tb_stage_process ADD CONSTRAINT stage_proces_fk_job FOREIGN KEY (vacancy_job_id) REFERENCES tb_job_vacancies;
ALTER TABLE tb_stage_process ADD CONSTRAINT stage_proces_fk_stage_selection FOREIGN KEY (stage_selection_id) REFERENCES tb_selection_stages;
ALTER TABLE tb_stage_process ADD CONSTRAINT stage_proces_fk_selection_status FOREIGN KEY (selection_status_id) REFERENCES tb_selection_statuses;

CREATE TABLE tb_stage_process_attachments(
    id text DEFAULT uuid_generate_v4() NOT NULL,
    stage_process_id TEXT NOT NULL,
    question_file_id TEXT NULL,
    answer_file_id TEXT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_stage_process_attachments ADD CONSTRAINT stage_process_attachment_pk PRIMARY KEY (id);
ALTER TABLE tb_stage_process_attachments ADD CONSTRAINT stage_process_attachment_fk_stage_process_id FOREIGN KEY (stage_process_id) REFERENCES tb_stage_process;
ALTER TABLE tb_stage_process_attachments ADD CONSTRAINT stage_process_attachment_fk_file_answer FOREIGN KEY (answer_file_id) REFERENCES tb_files;
ALTER TABLE tb_stage_process_attachments ADD CONSTRAINT stage_process_attachment_fk_file_question FOREIGN KEY (question_file_id) REFERENCES tb_files;

CREATE TABLE tb_offering_statuses(
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(50) NOT NULL,
    status VARCHAR(100) NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_offering_statuses ADD CONSTRAINT offering_statuses_pk PRIMARY KEY (id);

CREATE TABLE tb_offering (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    stage_process_id TEXT NOT NULL,
    offering_status_id TEXT NOT NULL,
    date_boarding DATE NOT NULL,
    salary BIGINT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_offering ADD CONSTRAINT offering_pk PRIMARY KEY (id);
ALTER TABLE tb_offering ADD CONSTRAINT offering_fk_offering_status FOREIGN KEY (offering_status_id) REFERENCES tb_offering_statuses;
ALTER TABLE tb_offering ADD CONSTRAINT offering_fk_stage_process FOREIGN KEY (stage_process_id) REFERENCES tb_stage_process;

CREATE TABLE tb_otp  (
    id text DEFAULT uuid_generate_v4() NOT NULL,
    code VARCHAR(10) NOT NULL,
    user_id TEXT,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_by TEXT NULL DEFAULT 'SYSTEM',
    updated_at TIMESTAMPTZ NULL,
    version INT NOT NULL DEFAULT 0,
    deleted_at TIMESTAMPTZ NULL
);

ALTER TABLE tb_otp ADD CONSTRAINT otp_pk PRIMARY KEY (id);



INSERT INTO tb_roles (code, name) VALUES 
('SA', 'Super Admin'),
('HR', 'HR Recruiter'),
('KD', 'Kandidat');

INSERT INTO tb_employment_type (code, name) VALUES 
('FT', 'Full Time'),
('PT', 'Part Time'),
('CT', 'Contract'),
('IT', 'Internship');

INSERT INTO tb_level_experiences (code, name) VALUES 
('EL', 'Entry Level'),
('ML', 'Mid Level'),
('SL', 'Senior Level');

INSERT INTO tb_job_statuses (code, status) VALUES
('PD', 'Pending'),
('OG', 'Ongoing'),
('ED', 'Ended');

INSERT INTO tb_selection_stages (code, name) VALUES
('APL', 'Applied'),
('IHR', 'Interview HR'),
('ASE', 'Assessment'),
('IUS', 'Interview User'),
('MCU', 'Medichal Check-up');

INSERT INTO tb_selection_statuses (code, name) VALUES 
('PDG', 'Pending'),
('PRS', 'Process'),
('SCD', 'On Schedule'),
('PAS', 'Passed'),
('FLD', 'Failed');

INSERT INTO tb_locations (name, code) VALUES 
('Jakarta Utara', 'JKU'), 
('Jakarta Barat', 'JKB'), 
('Jakarta Selatan', 'JKS');

SELECT * FROM tb_job_vacancies WHERE id = '2d6932ac-2ac1-423d-ad56-aefa22da09d1';
--
--INSERT INTO tb_users (username, password, email, role_id) VALUES
--('superadmin', 'superadmin', 'superadmin@example.com', 'a3ae6dc0-5f92-4499-8971-2dce75539e32');
--
--INSERT INTO tb_users (role_id, username, password, email) VALUES 
--('a3ae6dc0-5f92-4499-8971-2dce75539e32', 'admin', '$2a$12$6diu/gljZHDIV1Cuur6Nt.Nzoc5cGV51xwlKeuzvCPIHgM1Y/q7gO', 'admin@example.com');
--
--INSERT INTO tb_profile_users (name, phone, address, city, gender, image, file_cv, user_id) VALUES 
--('Super Admin', '081234567890', 'Jalan terbaik', 'Pontianak', 'L', 'image.png', 'cv.pdf', 1),
--('HR 1', '082345678901', 'Jalan kesehatan', 'Bogor', 'L', 'image.png', 'cv.pdf', 2),
--('HR 2', '083456789012', 'Jalan makmur', 'Surabaya', 'L', 'image.png', 'cv.pdf', 3),
--('HR 3', '084567890123', 'Jalan kesehatan', 'Bekasi', 'L', 'image.png', 'cv.pdf', 4),
--('Kandidat 1', '089012345678', 'Jalan gunung bromo', 'Tangerang', 'L', 'image.png', 'cv.pdf', 5),
--('Kandidat 2', '085567890123', 'Jalan gunung kidul', 'Bandung', 'L', 'image.png', 'cv.pdf', 6),
--('Kandidat 3', '086789012345', 'Jalan gunung rinjani', 'Bogor', 'L', 'image.png', 'cv.pdf', 7);
--
--INSERT INTO tb_user_educations (univ_name, major, start_date, end_date, user_id) VALUES
--('Institut A', 'Jurusan A', '2016-06-15', '2017-06-20', 1),
--('Institut B', 'Jurusan B', '2015-04-10', '2017-08-05', 2),
--('Institut C', 'Jurusan C', '2014-09-25', '2016-12-18', 3),
--('Institut D', 'Jurusan D', '2013-03-12', '2017-05-30', 4),
--('Institut E', 'Jurusan E', '2019-01-20', '2020-06-10', 5),
--('Institut F', 'Jurusan F', '2018-07-05', '2020-05-15', 6),
--('Institut G', 'Jurusan G', '2018-03-18', '2019-12-22', 7);


--INSERT INTO tb_user_experiences (company_name, position, start_date, end_date, user_id) VALUES
--('PT. Makmur Jaya', 'Junior Web Developer', '2018-05-01', '2020-03-15', 5),
--('PT. Maju Terus', 'Backend Developer', '2020-06-10', NULL, 6),
--('PT. Rezeki Abadi', 'Full Stack Web Developer', '2017-02-20', '2021-11-30', 7);
--
--INSERT INTO tb_user_certifications (name, file, user_id) VALUES 
--('Pemrograman Dasar', 'file.pdf', 5);

--INSERT INTO tb_jobs (title, overview, start_salary, end_salary, deadline_apply, publish_date, employment_type_id, level_experience_id, location_id) VALUES
--('Junior Web Developer', 'lorem ipsum dolor sit amet', 3000000, 5000000, '2024-11-30', '2024-10-01', 1, 1, 1),
--('Senior Backend Developer', 'lorem ipsum dolor sit amet', 8000000, 12000000, '2024-12-15', '2024-10-10', 2, 3, 2),
--('Full Stack Developer', 'lorem ipsum dolor sit amet', 6000000, 9000000, '2024-12-01', '2024-10-05', 1, 2, 3);

--INSERT INTO tb_job_descriptions (description, job_id) VALUES 
--('Lorem ipsum dolor sit amet.', 1),
--('Qui ipsum dolorum a delectus voluptas vel voluptatem natus', 1),
--('Sed laboriosam galisum ex voluptas exercitationem ad animi doloribus ex minus molestiae.', 1),
--('Quo sunt distinctio sit nihil sunt ea galisum voluptatem ut quam laboriosam At dolores doloremque ut facere natus.', 1),
--('Qui harum nobis qui laboriosam sint non error deleniti est impedit asperiores cum consequatur eaque.', 1),
--('Ut sed velit sed velit tincidunt viverra.', 2),
--('Nulla tincidunt, erat sit amet posuere tincidunt, nisl arcu.', 2),
--('Vestibulum condimentum nulla at orci sodales suscipit.', 2),
--('Curabitur placerat felis sed turpis euismod, vitae facilisis metus.', 2),
--('Integer malesuada mauris sed mi viverra, quis vestibulum ex consequat.', 2),
--('Donec euismod libero id turpis volutpat, vel malesuada ligula fermentum.', 3),
--('Vivamus cursus orci quis orci maximus, in vulputate odio dapibus.', 3),
--('Aliquam scelerisque purus non orci varius, nec gravida justo ornare.', 3),
--('Etiam bibendum eros ut dui malesuada, quis ultricies felis pretium.', 3),
--('Proin pharetra arcu eget dolor ultricies, id ultricies risus bibendum.', 3);

--INSERT INTO tb_job_spesifications (spesification, job_id) VALUES 
--('Lorem ipsum dolor sit amet.', 1),
--('Qui ipsum dolorum a delectus voluptas vel voluptatem natus', 1),
--('Sed laboriosam galisum ex voluptas exercitationem ad animi doloribus ex minus molestiae.', 1),
--('Quo sunt distinctio sit nihil sunt ea galisum voluptatem ut quam laboriosam At dolores doloremque ut facere natus.', 1),
--('Qui harum nobis qui laboriosam sint non error deleniti est impedit asperiores cum consequatur eaque.', 1),
--('Ut sed velit sed velit tincidunt viverra.', 2),
--('Nulla tincidunt, erat sit amet posuere tincidunt, nisl arcu.', 2),
--('Vestibulum condimentum nulla at orci sodales suscipit.', 2),
--('Curabitur placerat felis sed turpis euismod, vitae facilisis metus.', 2),
--('Integer malesuada mauris sed mi viverra, quis vestibulum ex consequat.', 2),
--('Donec euismod libero id turpis volutpat, vel malesuada ligula fermentum.', 3),
--('Vivamus cursus orci quis orci maximus, in vulputate odio dapibus.', 3),
--('Aliquam scelerisque purus non orci varius, nec gravida justo ornare.', 3),
--('Etiam bibendum eros ut dui malesuada, quis ultricies felis pretium.', 3),
--('Proin pharetra arcu eget dolor ultricies, id ultricies risus bibendum.', 3);
--
--INSERT INTO tb_job_transactions (job_id, job_status_id, user_id) VALUES 
--(1, 2, 2),
--(2, 2, 3),
--(3, 2, 4);
--
--INSERT INTO tb_apply_jobs (user_id, job_id) VALUES 
--(5, 1), 
--(5, 2), 
--(6, 1), 
--(6, 3), 
--(7, 2), 
--(7, 3); 
--
--INSERT INTO tb_apply_attachments (file, job_id, user_id) VALUES 
--('file.pdf', 1, 5),
--('file.pdf', 3, 6),
--('file.pdf', 2, 7);
--
--INSERT INTO tb_stage_process (score, date, user_id, job_id, stage_selection_id, selection_status_id) VALUES
--(85, '2024-10-01', 5, 1, 1, 4),
--(90, '2024-10-02', 5, 2, 2, 4),
--(78, '2024-10-03', 6, 1, 1, 5),
--(92, '2024-10-04', 6, 3, 3, 4),
--(0, '2024-10-05', 7, 2, 2, 3);
--
--INSERT INTO tb_offering (date_boarding, salary, user_id, job_id) VALUES
--('2024-11-01', 10000000, 1, 1),
--('2024-11-15', 12000000, 2, 1),
--('2025-02-01', 9000000, 3, 2),
--('2025-03-10', 11000000, 4, 3),
--('2025-01-20', 9500000, 5, 2);


--SELECT * FROM tb_users tu 
--JOIN tb_roles tr 
--ON tr.id = tu.role_id 
--JOIN tb_profile_users tpu 
--ON tpu.user_id = tu.id
--WHERE username = 'superadmin' AND password = 'superadmin';
--
--SELECT * FROM  tb_roles WHERE deleted_at IS NULL;
--
--SELECT * FROM  tb_roles WHERE id = 1 AND deleted_at IS NULL;
--
--SELECT * FROM tb_users tu
--JOIN tb_roles tr
--ON tr.id = tu.role_id
--WHERE tu.deleted_at IS NULL;
--
--SELECT tu.id AS id, role_id, username, password, email, code, tu.version AS version FROM tb_users tu
--JOIN tb_roles tr
--ON tr.id = tu.role_id 
--WHERE tu.id = 7 AND tu.deleted_at IS NULL;
--
--SELECT tu.id AS id, role_id, username, password, email, code, tu.version AS version, tu.is_active as is_active_user
--                FROM tb_users tu
--                JOIN tb_roles tr
--                ON tr.id = tu.role_id
--                WHERE tu.id = 7 AND tu.deleted_at IS NULL;
--                
--SELECT user_id, name, phone, address, city, gender FROM tb_profile_users tpu 
--JOIN tb_users tu 
--ON tu.id = tpu.user_id;
--
--                SELECT user_id, name, phone, address, city, gender FROM tb_profile_users tpu 
--                JOIN tb_users tu 
--                ON tu.id = tpu.user_id
--                WHERE tpu.id = 1 AND tpu.deleted_at IS NULL;
--                
--            SELECT * FROM tb_locations WHERE deleted_at IS NULL;
            
--SELECT tjt.* FROM tb_job_transactions tjt
--JOIN tb_job_vacancies tjv 
--ON tjv.id = tjt.job_vacancy_id
--WHERE tjt.job_vacancy_id = 1
--ORDER BY tjt.created_at 
--DESC LIMIT 1;