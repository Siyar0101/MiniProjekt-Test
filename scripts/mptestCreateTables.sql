use pplace


create table parkingspot (
       id int primary key identity(1,1),
       number int not null unique, -- natural PK
	   basemonthlyfee decimal(10,2)
);

create table employee (
       id int primary key identity(1,1),
       initials varchar(8) not null unique, --natural PK
       fname varchar(64) not null,
       lname varchar(64) not null,
       employmentdate date not null,
)

create table rentagreement (
       id int primary key identity(1,1),
       startdate date not null, -- natural PK part
       enddate date,
       monthlyfee decimal(10,2) not null,
       employee_id int not null,
       parkingspot_id int not null, -- natural PK part
       unique(startdate, parkingspot_id),-- natural PK part
       constraint fk_ra_empid foreign key(employee_id) references employee(id),
       constraint fk_ra_pspotid foreign key(parkingspot_id) references parkingspot(id)
)

create table vehicle (
      id int primary key identity(1,1),
      numberplate char(7) not null unique, --natural PK
	  electric bit not null,
      registrationdate date not null,
      employee_id int not null,
      constraint fk_v_empid foreign key(employee_id) references employee(id)
);
