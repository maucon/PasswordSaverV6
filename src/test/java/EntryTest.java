import de.tandem.psv6.database.Database;
import de.tandem.psv6.entity.Entry;
import de.tandem.psv6.entity.User;
import de.tandem.psv6.security.Security;
import org.identityconnectors.common.security.GuardedString;

public class EntryTest {
    public static void main(String[] args) {
        var username = "sadsa";
        var password = "1";

        var user = new User(username, Security.save_hash(username, password));
        Security.guardedString = new GuardedString(Security.key_hash(password).toCharArray());

//        Database.setupUser(user);

        var database = Database.createInstance(user.getUsername());
        System.out.println(Security.passwordMatches(user.getUsername(), "1"));
        database.addEntry(new Entry("Steam", "Swerik", "123", "Ass", ""));
//        database.addEntry(new Entry("new", "new", "new", "new", ""));
        System.out.println(database.getAllEntries());
//        Entry edit = database.getAllEntries().get(0);
//        edit.setLogin("Mario Kart2");
//        database.editEntry(edit);
//        System.out.println(database.getAllEntries());
    }
}
