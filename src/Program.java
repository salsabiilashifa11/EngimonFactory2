import java.util.Scanner;
public class Program {
    //Inisialisasi
    System.out.println("Selamat datang di Engimon Factory");
    String PlayerName;
    System.out.print("Masukkan nama player : ");
    Scanner scanner = new Scanner(System.in);
    PlayerName = scanner.nextLine();
    Player p(PlayerName);
//    Map m("map.txt", p);
//    int JumlahEngimon, JumlahEngimonAir, JumlahEngimonGrassland1, JumlahEngimonGrassland2, posisiX, posisiY, lvl, element;
//    cout<<"Masukkan jumlah engimon : ";
//    cin>>JumlahEngimon;
//    WildEngimon *Engimon = new WildEngimon[JumlahEngimon];
//    JumlahEngimonAir = JumlahEngimon/4;
//    JumlahEngimonGrassland1 = JumlahEngimonAir;
//    JumlahEngimonGrassland2 = JumlahEngimon - JumlahEngimonAir - JumlahEngimonGrassland1;
//    int j = 0;
//    srand (time(0));
//    int countTurn = 0;
//    m.updatePlayer(); //Moved here
//
//    //Inisialisasi Engimon di air
//    for (int i = 0; i < JumlahEngimonAir; i++){
//        posisiX = (rand() % 9) + 1;
//        posisiY = (rand() % 8) + 20;
//        lvl = (rand() % 49) + 1;
//        element = (rand() % 2);
//        switch (element){
//            case 0:
//                Engimon[j] = WildEngimon("kadal", "ice", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//            case 1:
//                Engimon[j] = WildEngimon("kambing", "water", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//        }
//        j++;
//    }
//
//    // Inisialisasi Engimon di Grassland yang bagian atas
//    for (int i = 0; i < JumlahEngimonGrassland1; i++){
//        posisiX = (rand() % 9) + 1;
//        posisiY = (rand() % 19) + 1;
//        lvl = (rand() % 49)+1;
//        element = (rand() % 3);
//        switch (element){
//            case 0:
//                Engimon[j] = WildEngimon("ikan", "fire", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//            case 1:
//                Engimon[j] = WildEngimon("kelelawar", "ground", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//            case 2:
//                Engimon[j] = WildEngimon("beruang", "electric", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//            default:
//                break;
//        }
//        j++;
//    }
//
//    // Inisialisasi Engimon di Grassland yang
//    for (int i = 0; i < JumlahEngimonGrassland2; i++){
//        posisiX = (rand() % 19) +10;
//        posisiY = (rand() % 29);
//        lvl = (rand() % 49 + 1);
//        element = (rand() % 3);
//        switch (element){
//            case 0:
//                Engimon[j] = WildEngimon("ikan", "fire", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//            case 1:
//                Engimon[j] = WildEngimon("kelelawar", "ground", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//            case 2:
//                Engimon[j] = WildEngimon("beruang", "electric", lvl, posisiX, posisiY, &m);
//                m.getCell(posisiX, posisiY).setEngimon(&Engimon[j]);
//                break;
//            default:
//                break;
//        }
//        j++;
//    }
//    m.drawMap();
//
//
//    try{
//        string command;
//        while (true) {
//            m.getPlayer().getPosition().print();
//            cout << "Command : ";
//            cin >> command;
//            cout << endl;
//            if (command == "w"){
//                m.getPlayer().Move('w');
//                m.updatePlayer();
//            }
//            else if (command == "a"){
//                m.getPlayer().Move('a');
//                m.updatePlayer();
//            }
//            else if (command == "s"){
//                m.getPlayer().Move('s');
//                m.updatePlayer();
//            }
//            else if (command  == "d"){
//                m.getPlayer().Move('d');
//                cout << "posisi owned x : " << m.getPlayer().getActiveEngimon().getPosition().getX() << endl;
//                cout << "posisi owned y : " << m.getPlayer().getActiveEngimon().getPosition().getY() << endl;
//                m.updatePlayer();
//            }else if (command == "q"){
//                break;
//            }else if (command == "display"){
//                cout << endl << "DISPLAY INVENTORY" << endl;
//                p.showInventory();
//            }
//            else if (command == "battle"){
//                cout << endl << "BATTLE" << endl;
//                if (m.getEnemy().getOccupierP().getName() != ""){
//                    cout << "Tidak ada musuh di sekitar player" << endl;
//                }
//                else{
//                    p.Battle(*(m.getEnemy().getOccupierE()));
//                }
//            }
//            else if (command == "breed"){
//                cout << endl << "BREED ENGIMON" << endl;
//                p.executeBreed();
//            }
//            else if (command == "change"){
//                cout << endl << "CHANGE ACTIVE ENGIMON" << endl;
//                p.changeActiveEngimon();
//            }
//            else if (command == "detail") {
//                cout << endl << "DETAIL ENGIMON" << endl;
//                string namaEngimon;
//                cout << "Masukkan nama engimon: ";
//                cin >> namaEngimon;
//                p.displayEngimon(namaEngimon);
//            }
//            else if (command == "learn") {
//                cout << endl << "USING SKILL ITEMS" << endl;
//                p.showItems();
//                cout << "Pilih nama skill item yang ingin dipakai : ";
//                string skillName;
//                cin.ignore();
//                std::getline(std::cin, skillName);
//                p.showEngimons();
//                cout << "Pilih nama engimon yang ingin diajari : ";
//                string eName;
//                cin >> eName;
//                p.teach(skillName,eName);
//            }
//            else if (command == "interact"){
//                cout << endl << "INTERACT WITH ACTIVE ENGIMON" << endl;
//                p.getActiveEngimon().interact();
//            }
//            else{
//                cout<<"Invalid Command"<<endl;
//            }
//
//
//
//            if (countTurn % 4 == 0)
//            {
//                for (int i = 0; i < JumlahEngimon; i++) {
//                    Engimon[i].Move(&m);
//                }
//            }
//
//            for (int i = 0; i < JumlahEngimon; i++) {
//                Engimon[i].assertPosition(&m);
//            }
//
//            m.drawMap();
//            countTurn++;
//
//        }
//    }
//    catch (const char* c) {
//        cout << "Exception: " << c << endl;
//    }
//
//    return 0;
}
