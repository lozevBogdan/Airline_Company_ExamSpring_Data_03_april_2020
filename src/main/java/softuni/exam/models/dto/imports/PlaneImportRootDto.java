package softuni.exam.models.dto.imports;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneImportRootDto {

    @XmlElement(name = "plane")
    private List<PlaneImportDto> planeImportDtos;

    public PlaneImportRootDto() {
    }

    public List<PlaneImportDto> getPlaneImportDtos() {
        return planeImportDtos;
    }

    public void setPlaneImportDtos(List<PlaneImportDto> planeImportDtos) {
        this.planeImportDtos = planeImportDtos;
    }
}
