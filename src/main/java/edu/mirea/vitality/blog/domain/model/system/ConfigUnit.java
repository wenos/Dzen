package edu.mirea.vitality.blog.domain.model.system;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "c_config_unit")
public class ConfigUnit {

    /**
     * Ключ конфигурации
     */
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "key", nullable = false, unique = true)
    private SystemPropertyKey key;

    /**
     * Тип поля
     */
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfigFieldType type;

    /**
     * Значение конфигурационной единицы
     */
    @Column(name = "value")
    private String value;

    /**
     * Изменение параметра обрабатывается другим сервисом, поле нужно для маркировки параметров на фронте
     * и для запрета изменения напрямую
     */
    @Column(name = "custom_handler")
    private Boolean customHandler;

    public Long getLongValue() {
        return type == ConfigFieldType.NUMBER ? Long.parseLong(value) : null;
    }

    public Boolean getBooleanValue() {
        return type == ConfigFieldType.BOOLEAN ? Boolean.parseBoolean(value) : null;
    }

    public String getStringValue() {
        return type == ConfigFieldType.STRING ? value : null;
    }
}
