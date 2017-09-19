package com.emergya.sss3E.persistence.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class is a management contract for the different Entity types.
 *
 * @author Maria Angeles Villaba mavillalba@emergya.com
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    protected Long id;
}
