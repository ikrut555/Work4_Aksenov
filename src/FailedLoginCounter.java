import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FailedLoginCounter {
    String emaail; // почта, полчаемая в случае ошибка логина. Далее добавится в лог
    List<String> badEmails; // Список плохих почт, которые встречаются в логе более 4 раз. Каждый раз формируется заново по логу
    public FailedLoginCounter(String emaail) throws IOException {
        File file2 = new File("c://Intel//log.txt"); // запись в лог
        Stream<String> lines = Files.lines(Path.of("c://Intel//log.txt")); // чтение из лога для дальнейшей его перезаписи
        String data = lines.collect(Collectors.joining("\n"));

        this.emaail = emaail;
        this.badEmails = new ArrayList<>();

        FileWriter writer3 = new FileWriter(file2);
        writer3.write(data+ "\n"+emaail+"\n"); // записываем в лог прошлые данные лога + новая почта, которая вызвала ошибку
        writer3.close();

        List<String> content4 = Files.readAllLines(Paths.get("c://Intel//log.txt")); // Читаем данные из лога, чтобы составить список почт
        for (String email: content4) {
            if (content4.stream().filter(inContent -> email.equals(inContent)).count() > 4) { // Если почта в логе(теперь в списке) встречается больше 4 раз, то попадает в список плохих почт
                if (!badEmails.contains(email)) { // избегаем дублирования в списке плохих почт
                    badEmails.add(email);
                }
            }
        }

    }
}
