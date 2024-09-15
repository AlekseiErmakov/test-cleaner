package com.erm.test.cleaner;

import java.util.stream.Stream;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.erm.test.cleaner.StatementType.DELETE;
import static com.erm.test.cleaner.StatementType.INSERT;
import static com.erm.test.cleaner.StatementType.SELECT;
import static com.erm.test.cleaner.StatementType.TRUNCATE;
import static com.erm.test.cleaner.StatementType.UPDATE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

class StatementTypeTest {

    @ParameterizedTest
    @MethodSource("statements")
    void isOdd_ShouldReturnTrueForOddNumbers(Statement statement) {
        assertThat(StatementType.from(statement).isModifying())
                .isTrue();
    }

    @Test
    void shouldReturnCorrectEnumValue() {
        assertThat(StatementType.from(mock(Insert.class)))
                .isEqualTo(INSERT);
        assertThat(StatementType.from(mock(Update.class)))
                .isEqualTo(UPDATE);
        assertThat(StatementType.from(mock(Delete.class)))
                .isEqualTo(DELETE);
        assertThat(StatementType.from(mock(Truncate.class)))
                .isEqualTo(TRUNCATE);
        assertThat(StatementType.from(mock(Select.class)))
                .isEqualTo(SELECT);
    }

    private static Stream<Statement> statements() {
        return Stream.of(
                mock(Insert.class),
                mock(Update.class),
                mock(Delete.class),
                mock(Truncate.class)
        );
    }
}