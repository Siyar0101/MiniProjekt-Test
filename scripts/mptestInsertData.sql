use pplace

insert into parkingspot 
       (number, basemonthlyfee) values
       (1, 400.00),
       (2, 300.00),
       (3, 200.00),
       (4, 500.00),
       (5, 300.00),
       (6, 500.00);

insert into employee
      (initials, fname, lname, employmentdate) values
	  ('aand', 'Anders', 'And', '2020-12-01'),
	  ('fml', 'Fedt', 'Mule', '2021-07-01'),
	  ('asa', 'Andersine', 'And', '2024-05-01'),
	  ('rand', 'Rap', 'And', '2024-09-01'), -- ingen bil, for ung!
	  ('rund', 'Rup', 'And', '2025-08-01'), -- knallert
	  ('rind', 'Rip', 'And', '2026-01-01'); -- knallert

insert into rentagreement
          (startdate, enddate, monthlyfee, employee_id, parkingspot_id) values
          ('2025-01-01', '2026-12-31', 300.00, 2, 1), -- fedtmule, spot 1 (green)
	      ('2025-07-01', '2032-01-01', 240.00, 3, 2),--, -- andersine, spot 2 (green)
	      ('2025-09-01', '2027-09-01', 200.00, 5, 3),--, -- rup, spot 3	(no green)       
	      ('2026-01-01', '2026-12-31', 475.00, 1, 4); -- anders, spot 4 (no green)
	  

insert into vehicle 
          (numberplate, electric, registrationdate, employee_id) values
          ('AA13133', 0, '2025-12-01', 1), --anders' benzinbil
          ('AA13134', 1, '2026-02-01', 1), --anders' elbil
          ('FM12122', 1, '2025-01-15', 2), --fedtmules elbil
          ('FM12123', 1, '2026-01-15', 2), --fedtmules elbil
          ('AN69699', 1, '2025-05-01', 3), --andersines elbil
          ('CY8921', 0, '2025-08-01', 5), --rup 
          ('VS9433', 0, '2025-03-05', 6); --rip 