CREATE TABLE IF NOT EXISTS public.vacancy_label
(
    vacancy_id bigint NOT NULL,
    label_id bigint NOT NULL,
    CONSTRAINT pk_vacancy_label PRIMARY KEY (vacancy_id, label_id),
    CONSTRAINT fk_vacancies_vacancy_id FOREIGN KEY (vacancy_id)
    REFERENCES public.vacancies (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_labels_label_id FOREIGN KEY (label_id)
    REFERENCES public.labels (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )