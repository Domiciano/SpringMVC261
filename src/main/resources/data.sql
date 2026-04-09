

INSERT INTO app_user (username, password) VALUES ('luisdiaz@gmail.com','$2a$12$LE5wWF2zJKLfE98E4KgJPO.buVfS0xHlSg2F2ciQMnk5kdgEBx506');
INSERT INTO app_user (username, password) VALUES ('domic.rincon@gmail.com','$2a$12$LE5wWF2zJKLfE98E4KgJPO.buVfS0xHlSg2F2ciQMnk5kdgEBx506');



-- Profesores
INSERT INTO professor (name) VALUES ('Juan Perez');
INSERT INTO professor (name) VALUES ('Maria Rodriguez');
INSERT INTO professor (name) VALUES ('Carlos Gomez');

-- Cursos
INSERT INTO course (name, credits, professor_id) VALUES ('Introduccion a la Programacion', 4, 1);
INSERT INTO course (name, credits, professor_id) VALUES ('Estructuras de Datos', 4, 1);
INSERT INTO course (name, credits, professor_id) VALUES ('Anatomia Humana', 5, 2);
INSERT INTO course (name, credits, professor_id) VALUES ('Fisiologia', 5, 2);
INSERT INTO course (name, credits, professor_id) VALUES ('Derecho Penal', 3, 3);
INSERT INTO course (name, credits, professor_id) VALUES ('Historia del Arte', 3, 3);

-- Estudiantes
INSERT INTO student (name, code, program) VALUES ('Laura Garcia', '2021102001', 'Ingenieria de Sistemas');
INSERT INTO student (name, code, program) VALUES ('Pedro Pascal', '2021102002', 'Ingenieria de Sistemas');
INSERT INTO student (name, code, program) VALUES ('Andres Lopez', '2021102003', 'Medicina');
INSERT INTO student (name, code, program) VALUES ('Sofia Torres', '2021102004', 'Derecho');
INSERT INTO student (name, code, program) VALUES ('Camila Velez', '2021102005', 'Medicina');

-- Inscripciones (student_course)
INSERT INTO student_course (student_id, course_id) VALUES (1, 1);
INSERT INTO student_course (student_id, course_id) VALUES (1, 2);
INSERT INTO student_course (student_id, course_id) VALUES (2, 1);
INSERT INTO student_course (student_id, course_id) VALUES (3, 3);
INSERT INTO student_course (student_id, course_id) VALUES (3, 4);
INSERT INTO student_course (student_id, course_id) VALUES (4, 5);
INSERT INTO student_course (student_id, course_id) VALUES (4, 6);
INSERT INTO student_course (student_id, course_id) VALUES (5, 3);


-- ─── PERMISOS: 4 operaciones × 4 tablas = 16 permisos ───────────────────────
INSERT INTO permission (name) VALUES ('CREATE_STUDENT');    -- 1
INSERT INTO permission (name) VALUES ('READ_STUDENT');      -- 2
INSERT INTO permission (name) VALUES ('UPDATE_STUDENT');    -- 3
INSERT INTO permission (name) VALUES ('DELETE_STUDENT');    -- 4
INSERT INTO permission (name) VALUES ('CREATE_PROFESSOR');  -- 5
INSERT INTO permission (name) VALUES ('READ_PROFESSOR');    -- 6
INSERT INTO permission (name) VALUES ('UPDATE_PROFESSOR');  -- 7
INSERT INTO permission (name) VALUES ('DELETE_PROFESSOR');  -- 8
INSERT INTO permission (name) VALUES ('CREATE_ENROLLMENT'); -- 9
INSERT INTO permission (name) VALUES ('READ_ENROLLMENT');   -- 10
INSERT INTO permission (name) VALUES ('UPDATE_ENROLLMENT'); -- 11
INSERT INTO permission (name) VALUES ('DELETE_ENROLLMENT'); -- 12
INSERT INTO permission (name) VALUES ('CREATE_COURSE');     -- 13
INSERT INTO permission (name) VALUES ('READ_COURSE');       -- 14
INSERT INTO permission (name) VALUES ('UPDATE_COURSE');     -- 15
INSERT INTO permission (name) VALUES ('DELETE_COURSE');     -- 16

-- ─── ROLES ───────────────────────────────────────────────────────────────────
INSERT INTO role (name) VALUES ('ADMIN');    -- 1
INSERT INTO role (name) VALUES ('DIRECTOR'); -- 2

-- ─── ADMIN: todos los permisos (1 – 16) ──────────────────────────────────────
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 3);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 4);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 5);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 6);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 7);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 8);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 9);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 10);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 11);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 12);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 13);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 14);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 15);
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 16);

-- ─── DIRECTOR: leer todo + CRUD completo de Enrollment ───────────────────────
--   READ de todas las tablas
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 2);  -- READ_STUDENT
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 6);  -- READ_PROFESSOR
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 14); -- READ_COURSE
--   CRUD completo de Enrollment
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 9);  -- CREATE_ENROLLMENT
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 10); -- READ_ENROLLMENT
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 11); -- UPDATE_ENROLLMENT
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 12); -- DELETE_ENROLLMENT

-- ─── USUARIOS (contraseña en texto plano solo para pruebas) ──────────────────
INSERT INTO app_user (username, password) VALUES ('carlos',  '{noop}admin123');    -- 1
INSERT INTO app_user (username, password) VALUES ('maria',   '{noop}admin123');    -- 2
INSERT INTO app_user (username, password) VALUES ('ana',     '{noop}dir123');      -- 3
INSERT INTO app_user (username, password) VALUES ('juan',    '{noop}dir123');      -- 4
INSERT INTO app_user (username, password) VALUES ('sofia',   '{noop}dir123');      -- 5

-- ─── ASIGNACIÓN DE ROLES ─────────────────────────────────────────────────────
INSERT INTO user_role (user_id, role_id) VALUES (1, 1); -- carlos  → ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 1); -- maria   → ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (3, 2); -- ana     → DIRECTOR
INSERT INTO user_role (user_id, role_id) VALUES (4, 2); -- juan    → DIRECTOR
INSERT INTO user_role (user_id, role_id) VALUES (5, 2); -- sofia   → DIRECTOR


-- 10 Profesores
INSERT INTO professor (name) VALUES ('Juan Perez');
INSERT INTO professor (name) VALUES ('Maria Rodriguez');
INSERT INTO professor (name) VALUES ('Carlos Gomez');
INSERT INTO professor (name) VALUES ('Ana Martinez');
INSERT INTO professor (name) VALUES ('Luis Hernandez');
INSERT INTO professor (name) VALUES ('Pedro Sanchez');
INSERT INTO professor (name) VALUES ('Rosa Moreno');
INSERT INTO professor (name) VALUES ('Jorge Castillo');
INSERT INTO professor (name) VALUES ('Elena Vargas');
INSERT INTO professor (name) VALUES ('Miguel Torres');

-- 50 Cursos
-- Juan Perez (Sistemas)
INSERT INTO course (name, credits, professor_id) VALUES ('Introduccion a la Programacion', 4, 1);
INSERT INTO course (name, credits, professor_id) VALUES ('Estructuras de Datos', 4, 1);
INSERT INTO course (name, credits, professor_id) VALUES ('Algoritmos y Complejidad', 4, 1);
INSERT INTO course (name, credits, professor_id) VALUES ('Base de Datos', 3, 1);
INSERT INTO course (name, credits, professor_id) VALUES ('Redes de Computadores', 3, 1);

-- Maria Rodriguez (Medicina)
INSERT INTO course (name, credits, professor_id) VALUES ('Anatomia Humana', 5, 2);
INSERT INTO course (name, credits, professor_id) VALUES ('Fisiologia', 5, 2);
INSERT INTO course (name, credits, professor_id) VALUES ('Bioquimica Medica', 4, 2);
INSERT INTO course (name, credits, professor_id) VALUES ('Farmacologia', 4, 2);
INSERT INTO course (name, credits, professor_id) VALUES ('Patologia General', 5, 2);

-- Carlos Gomez (Derecho)
INSERT INTO course (name, credits, professor_id) VALUES ('Derecho Penal', 3, 3);
INSERT INTO course (name, credits, professor_id) VALUES ('Derecho Civil', 3, 3);
INSERT INTO course (name, credits, professor_id) VALUES ('Derecho Constitucional', 3, 3);
INSERT INTO course (name, credits, professor_id) VALUES ('Derecho Laboral', 3, 3);
INSERT INTO course (name, credits, professor_id) VALUES ('Derecho Internacional', 2, 3);

-- Ana Martinez (Arquitectura)
INSERT INTO course (name, credits, professor_id) VALUES ('Dibujo Tecnico', 2, 4);
INSERT INTO course (name, credits, professor_id) VALUES ('Diseno Arquitectonico I', 4, 4);
INSERT INTO course (name, credits, professor_id) VALUES ('Diseno Arquitectonico II', 4, 4);
INSERT INTO course (name, credits, professor_id) VALUES ('Historia de la Arquitectura', 3, 4);
INSERT INTO course (name, credits, professor_id) VALUES ('Materiales de Construccion', 3, 4);

-- Luis Hernandez (Historia y Arte)
INSERT INTO course (name, credits, professor_id) VALUES ('Historia del Arte', 3, 5);
INSERT INTO course (name, credits, professor_id) VALUES ('Historia Universal', 3, 5);
INSERT INTO course (name, credits, professor_id) VALUES ('Historia de Colombia', 3, 5);
INSERT INTO course (name, credits, professor_id) VALUES ('Filosofia Moderna', 2, 5);
INSERT INTO course (name, credits, professor_id) VALUES ('Etica y Sociedad', 2, 5);

-- Pedro Sanchez (Matematicas)
INSERT INTO course (name, credits, professor_id) VALUES ('Calculo Diferencial', 4, 6);
INSERT INTO course (name, credits, professor_id) VALUES ('Calculo Integral', 4, 6);
INSERT INTO course (name, credits, professor_id) VALUES ('Algebra Lineal', 4, 6);
INSERT INTO course (name, credits, professor_id) VALUES ('Ecuaciones Diferenciales', 3, 6);
INSERT INTO course (name, credits, professor_id) VALUES ('Estadistica y Probabilidad', 3, 6);

-- Rosa Moreno (Fisica)
INSERT INTO course (name, credits, professor_id) VALUES ('Fisica Mecanica', 4, 7);
INSERT INTO course (name, credits, professor_id) VALUES ('Fisica Electromagnetismo', 4, 7);
INSERT INTO course (name, credits, professor_id) VALUES ('Fisica Moderna', 3, 7);
INSERT INTO course (name, credits, professor_id) VALUES ('Laboratorio de Fisica I', 2, 7);
INSERT INTO course (name, credits, professor_id) VALUES ('Laboratorio de Fisica II', 2, 7);

-- Jorge Castillo (Quimica)
INSERT INTO course (name, credits, professor_id) VALUES ('Quimica General', 4, 8);
INSERT INTO course (name, credits, professor_id) VALUES ('Quimica Organica', 4, 8);
INSERT INTO course (name, credits, professor_id) VALUES ('Quimica Analitica', 3, 8);
INSERT INTO course (name, credits, professor_id) VALUES ('Laboratorio de Quimica', 2, 8);
INSERT INTO course (name, credits, professor_id) VALUES ('Quimica Ambiental', 3, 8);

-- Elena Vargas (Biologia)
INSERT INTO course (name, credits, professor_id) VALUES ('Biologia Celular', 4, 9);
INSERT INTO course (name, credits, professor_id) VALUES ('Genetica', 4, 9);
INSERT INTO course (name, credits, professor_id) VALUES ('Microbiologia', 3, 9);
INSERT INTO course (name, credits, professor_id) VALUES ('Ecologia', 3, 9);
INSERT INTO course (name, credits, professor_id) VALUES ('Botanica', 3, 9);

-- Miguel Torres (Economia)
INSERT INTO course (name, credits, professor_id) VALUES ('Microeconomia', 3, 10);
INSERT INTO course (name, credits, professor_id) VALUES ('Macroeconomia', 3, 10);
INSERT INTO course (name, credits, professor_id) VALUES ('Economia Internacional', 3, 10);
INSERT INTO course (name, credits, professor_id) VALUES ('Finanzas Corporativas', 4, 10);
INSERT INTO course (name, credits, professor_id) VALUES ('Contabilidad General', 3, 10);
