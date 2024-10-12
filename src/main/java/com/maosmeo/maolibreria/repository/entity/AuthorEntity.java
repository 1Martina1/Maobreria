package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "AUTHORS")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name="NAME")
    private String name;
    @Column(name="SURNAME")
    private String surname;
    @Column(name="BIRTHDATE")
    private Long birthDate;
    @Column(name="DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<BookEntity> books;
}

/*


Fare un servizio che prende in input una lettera opzioinale, (es. "C"), OK
 e recupera tutti gli autori il cui name inizia con la lettera ricevuta in input OK
 Se non ci arriva una lettera in input leggerli tutti OK
 Ordinarli in ordine alfabetico per nome ok


Per ogni autore restituire tutti i suoi libri che sono usciti negli ultimi tre anni
e calcolare la media del prezzo di tutti i suoi libri OK


Restituire la seguente response:
    {
     "authors": [
        "fullName" : "name+Surname"
        "birthDate": "02-07-1987", (trasformare timestamp in data stringosa)
        "description": "description"
        "averageBookPrice: 16.34,
        "latestBooks": [
            {
                "name": "nomeLibro",
                "price":12.2,
                "publicationDate": "12-05-2023" (trasformare timestamp long in data string),
                "plot":"bellaStoria"
            }
        ]
     ]
    }



 */