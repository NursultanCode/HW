package HW;


import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import jdk.jshell.execution.Util;
import server.ContentType;
import server.ResponseCodes;
import server.Server;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HwServer extends Server {
    private final static Configuration freemarker = initFreeMarker();

    public HwServer(String host, int port) throws IOException {
        super(host, port);
        registerGet("/users", exchange -> freemarkerSampleHandler(exchange,"users.html"));
        registerGet("/books", exchange -> freemarkerSampleHandler(exchange,"books.html"));
        registerGet("/login",this::loginGet);
        registerPost("/login",this::loginPost);
    }

    private void loginPost(HttpExchange exchange) {
        String cType = getContentType(exchange);
        String raw = getBody(exchange);
        Map<String,String> parsed = Utils.parseUrlEncoded(raw,"&");
        String fmt = "<p> text:<b>%s</b></p>"
                +"<p> content type:<b>%s</b></p>"
                +"<p> after:<b>%s</b></p>";
        String data = String.format(fmt, raw,cType,parsed);
        try {
            sendByteData(exchange,ResponseCodes.OK, ContentType.TEXT_PLAIN, data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(input,utf8);
        try(BufferedReader reader = new BufferedReader(isr)){
            return reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getContentType(HttpExchange exchange) {
        return exchange.getRequestHeaders().getOrDefault("Content-Type", List.of("")).get(0);
    }


    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("index.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void freemarkerSampleHandler(HttpExchange exchange, String path) {
        if (path.equals("users.html")){
            renderTemplate(exchange, path, getUserModel());
        }else {
            renderTemplate(exchange, path, new BookModel());
        }

    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            // загружаем шаблон из файла по имени.
            // шаблон должен находится по пути, указанном в конфигурации
            Template temp = freemarker.getTemplate(templateFile);

            // freemarker записывает преобразованный шаблон в объект класса writer
            // а наш сервер отправляет клиенту массивы байт
            // по этому нам надо сделать "мост" между этими двумя системами

            // создаём поток который сохраняет всё, что в него будет записано в байтовый массив
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // создаём объект, который умеет писать в поток и который подходит для freemarker
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                // обрабатываем шаблон заполняя его данными из модели
                // и записываем результат в объект "записи"
                temp.process(dataModel, writer);
                writer.flush();

                // получаем байтовый поток
                var data = stream.toByteArray();

                // отправляем результат клиенту
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private UserModel getUserModel() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new UserModel();
    }
}

