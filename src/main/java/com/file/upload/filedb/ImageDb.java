package com.file.upload.filedb;

import com.file.upload.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ImageDb extends BaseEntity {

    @Column(name = "file_name")
    private String fileName;

    @Column(name="type")
    private String type;

    @Lob
    @Column(name="file")
    private byte[] file;
}
