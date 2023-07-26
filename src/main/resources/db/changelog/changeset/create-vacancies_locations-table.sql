CREATE TABLE IF NOT EXISTS public.vacancy_location
(
    vacancy_id bigint NOT NULL,
    location_id bigint NOT NULL,
    CONSTRAINT vacancy_location_pkey PRIMARY KEY (vacancy_id, location_id),
    CONSTRAINT fk_vacancy_vacancy_id FOREIGN KEY (vacancy_id)
        REFERENCES public.vacancies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_location_location_id FOREIGN KEY (location_id)
        REFERENCES public.locations (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)