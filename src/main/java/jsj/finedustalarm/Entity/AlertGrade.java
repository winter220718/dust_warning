package jsj.finedustalarm.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum AlertGrade {
    // 등급
    GRADE_1(1),
    GRADE_2(2),
    GRADE_3(3),
    GRADE_4(4);

    AlertGrade(int alertGrade) {

    }
}
