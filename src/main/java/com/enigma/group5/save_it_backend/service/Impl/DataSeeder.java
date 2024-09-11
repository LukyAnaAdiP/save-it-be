package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.dto.request.CategoryRequest;
import com.enigma.group5.save_it_backend.dto.request.NewProductRequest;
import com.enigma.group5.save_it_backend.dto.request.NewVendorRequest;
import com.enigma.group5.save_it_backend.entity.*;
import com.enigma.group5.save_it_backend.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final VendorService vendorService;
    private final ProductService productService;
    private final VendorProductService vendorProductService;
    private final GoodsCategoryService goodsCategoryService;
    private final ProductCategoryService productCategoryService;

    private List<NewVendorRequest> createNewVendorRequests() {
        List<NewVendorRequest> listVendors = new ArrayList<>();

        listVendors.add(NewVendorRequest.builder()
                .name("Indofood")
                .email("corporate.secretary@indofood.co.id")
                .address("Indofood Tower, 23th Floor Jl. Jend. Sudirman Kav 76-78 Jakarta 12910 ")
                .mobilePhoneNo("082157882211")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Coca Cola")
                .email("info@coca-cola.co.id")
                .address("Gedung South Quarter Tower B, Penthouse Floor, Cilandak Barat, Jakarta Selatan.")
                .mobilePhoneNo("08001002653")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Mayora")
                .email("consumer@mayora.co.id")
                .address("Gedung Mayora Jl. Tomang Raya Kav 21 – 23, Jakarta Barat")
                .mobilePhoneNo("08218207704")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Sony")
                .email("contact@sony.com")
                .address("Wisma GKBI, 23rd Floor, Suite 2301 Jl. Jend. Sudirman No.28 Jakarta 10210 Indonesia ")
                .mobilePhoneNo("08294987771")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("LG")
                .email("contact@lg.com")
                .address("Gandaria 8 Office Tower, lantai 29 BC & 31 ABCD, Jalan Sultan Iskandar Muda, Kelurahan Kebayoran Lama Utara, Kecamatan Kebayoran Lama, Jakarta Selatan 12240.")
                .mobilePhoneNo("08502201401")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Samsung")
                .email("cs.care@samsung.com")
                .address("Jl. Jababeka Raya Blok. F29-33, Cikarang, Jawa Barat 17530, Indonesia")
                .mobilePhoneNo("08214528901")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Batik Keris")
                .email("batikkeris@keris.co.id")
                .address("PT Batik Keris Jl. Taman Kebon Sirih III/15 Jakarta Pusat 10250")
                .mobilePhoneNo("08131468801")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Erigo")
                .email("partnership@erigostore.co.id")
                .address("Raya KM No.5, Legok, Tangerang Regency, Erigo Fulfillment Centre, Kab. Tangerang, Banten 15820, Indonesia")
                .mobilePhoneNo("0811912722")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Sophie Paris")
                .email("customer.care@theselfinc.com ")
                .address("Jl. Ciputat Raya No.123, RT.1/RW.8, Pd. Pinang, Kec. Kby. Lama, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12110 ")
                .mobilePhoneNo("08782138001")
                .build());

        listVendors.add(NewVendorRequest.builder()
                .name("Eiger")
                .email("marketing@eksonindo.co.id")
                .address("Jl. Terusan kopo Soreang Km. 11.5 No. 90A, Cilampeni, Kec. Katapang, Bandung, Jawa Barat 40921")
                .mobilePhoneNo("0811222206")
                .build());

        listVendors.add(NewVendorRequest.builder().name("NOSA")
                .email("contact@nosa.com")
                .address("Jl. Adiaksa Raya No.33, RT.7/RW.7, Lb. Bulus, Cilandak, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12440")
                .mobilePhoneNo("0827890123")
                .build());

        listVendors.add(NewVendorRequest.builder().name("3Second")
                .email("contact@3second.com")
                .address("Jln. Cimincrang No 9B Cimenerang (Cimincrang), Gedebage, Bandung, Jawa Barat, 40294")
                .mobilePhoneNo("0828901234")
                .build());

        listVendors.add(NewVendorRequest.builder().name("KALBE")
                .email("corp.comm@kalbe.co.id")
                .address("Gedung KALBE Jl. Let. Jend Suprapto Kav 4 Jakarta 10510 - Indonesia")
                .mobilePhoneNo("08248888911")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Sido Muncul")
                .email("info@sidomuncul.co.id")
                .address("Office Sido Muncul, 1st Floor, Gedung Hotel Tentrem Jl. Gajahmada No. 123, Semarang, Central Java 50134 ")
                .mobilePhoneNo("0869288112")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Blackmores")
                .email("info@blackmores.com")
                .address("Gedung KALBE Jl. Let. Jend Suprapto Kav 4 Jakarta 10510 - Indonesia")
                .mobilePhoneNo("0889734567")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Autovision")
                .email("contact@autovision.com")
                .address("Jl. Green Lake City Boulevard No.A-09, RT.001/RW.002, Gondrong, Kec. Cipondoh, Kota Tangerang, Banten 15147, Indonesia")
                .mobilePhoneNo("08122319691")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Mobi")
                .email("contact@mobi.com")
                .address("Jl. Dewi Sartika No.255, RT.8/RW.5, Cawang, Kec. Kramat jati, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13630")
                .mobilePhoneNo("08801555521")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Pertamina")
                .email("pcc135@pertamina.com")
                .address("Grha Pertamina, Jl. Medan Merdeka Timur No.11-13, Jakarta 10110 Indonesia")
                .mobilePhoneNo("08454613522")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Matoa")
                .email("contact@matoa.com")
                .address(" Jl. Setrasari Kulon III,No. 10-12 Sukarasa, Sukasari, Bandung, Jawa Barat, Area 51")
                .mobilePhoneNo("08400300022")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Kumpulan Kreasi")
                .email("contact@kumpulankreasi.com")
                .address("Sarua Makmur Blok IV No. 7 Sarua Ciputat Tangerang Selatan 1541")
                .mobilePhoneNo("0874639545")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Airsoft Indo")
                .email("cs@airsoftindo.co.id")
                .address("Baywalk Mall, Lantai 3 no 1A/B, Jakarta Utara.")
                .mobilePhoneNo("08528589991")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Wardah")
                .email("marketing@wardahbeauty.com")
                .address("Jl. Kp. Baru IV No.1 5, RT.5/RW.2, Ulujami, Kec. Pesanggrahan, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12250")
                .mobilePhoneNo("08158524941")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Emina")
                .email("customercare@paracorpgroup.com")
                .address("Jl. Poltangan I No.15, RT.1/RW.RT`01/10, Pejaten Timur, Ps. Minggu, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12510")
                .mobilePhoneNo("08772300011")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Sariayu")
                .email("customer_care@martinaberto.co.id")
                .address("Jl. Mustika Ratu No.2 7, RT.7/RW.8, Ciracas, Kec. Ciracas, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13740")
                .mobilePhoneNo("0887712911")
                .build());

        listVendors.add(NewVendorRequest.builder().name("IKEA Indonesia")
                .email("cs@IKEA.co.id")
                .address("Jl. Jalur Sutera Boulevard No. 45 Alam Sutera, Tangerang 15144")
                .mobilePhoneNo("08999090011")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Ace Hardware Indonesia")
                .email("ask_ace@ahi.id")
                .address("Jl. TB Simatupang No.3, RT.3/RW.2, Tj. Bar., Kec. Jagakarsa, Jakarta, Daerah Khusus Ibukota Jakarta 13760")
                .mobilePhoneNo("08150105821")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Informa")
                .email("informa.care@homecenter.co.id")
                .address("Jl. Puri Kencana No. 1 6, RT.6/RW.2 , Kembangan Sel., Kec. Kembangan, Kota Jakarta Barat, Daerah Khusus Ibokota Jakarta 11510 ")
                .mobilePhoneNo("08588282822")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Decathlon Indonesia")
                .email("contact.indonesia@decathlon.com")
                .address("Jl. Metro Pondok Indah Blk. BB No.3, RT.1/RW.16, Pd. Pinang, Kec. Kby. Lama, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12310")
                .mobilePhoneNo("0807717212")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Nike Indonesia")
                .email("NikeConsumer.services@nike.com.")
                .address("Lt. 3 Grand Indonesia Sky Bridge Jl. MH Thamrin No. 1 Jakarta, Jakarta, 10310")
                .mobilePhoneNo("08251810851")
                .build());

        listVendors.add(NewVendorRequest.builder().name("Sportline Indonesia")
                .email("contact@sportline.co.id")
                .address("Tower Ciputra World 2, Jl. Prof. DR. Satrio No.Kav. 11, RT.3/RW.3, Karet Semanggi, Setia Budi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12950")
                .mobilePhoneNo("08217811251")
                .build());

        return listVendors;
    }

    private List<NewProductRequest> createNewProductRequest() {

        List<NewProductRequest> listProduct = new ArrayList<>();

        MultipartFile threeSecondBaseballCapImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/3Second_Baseball_Cap.png", "3Second_Baseball_Cap.png", "image/png", (byte[]) null);

        MultipartFile threeSecondLeatherBeltImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/3Second_Leather_Belt.png", "3Second_Leather_Belt.png", "image/png", (byte[]) null);

        MultipartFile threeSecondRetroSunglassesImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/3Second_Retro_Sunglasses.png", "3Second_Retro_Sunglasses.png", "image/png", (byte[]) null);

        MultipartFile AceHardwareCookwareSetImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Ace_Hardware_Cookware_Set.png", "Ace_Hardware_Cookware_Set.png", "image/png", (byte[]) null);

        MultipartFile AceHardwareStorageBoxImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Ace_Hardware_Storage_Box.png", "Ace_Hardware_Storage_Box.png", "image/png", (byte[]) null);

        MultipartFile AceHardwareVacuumCleanerImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Ace_Hardware_Vacuum_Cleaner.png", "Ace_Hardware_Vacuum_Cleaner.png", "image/png", (byte[]) null);

        MultipartFile AirsoftIndoBBPelletsImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Airsoft_Indo_BB_Pellets.png", "Airsoft_Indo_BB_Pellets.png", "image/png", (byte[]) null);

        MultipartFile AirsoftIndoM4CarbineReplicaImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Airsoft_Indo_M4_Carbine_Replica.png", "Airsoft_Indo_M4_Carbine_Replica.png", "image/png", (byte[]) null);

        MultipartFile AirsoftIndoTacticalVestImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Airsoft_Indo_Tactical_Vest.png", "Airsoft_Indo_Tactical_Vest.png", "image/png", (byte[]) null);

        MultipartFile AutovisionDashboardCoverImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Autovision_Dashboard_Cover.png", "Autovision_Dashboard_Cover.png", "image/png", (byte[]) null);

        MultipartFile AutovisionLEDHeadlightBulbImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Autovision_LED_Headlight_Bulb.png", "Autovision_LED_Headlight_Bulb.png", "image/png", (byte[]) null);

        MultipartFile AutovisionRearviewCameraImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Autovision_Rearview_Camera.png", "Autovision_Rearview_Camera.png", "image/png", (byte[]) null);

        MultipartFile BatikKerisALineBatikSkirtImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Batik_Keris_A-Line_Batik_Skirt.png", "Batik_Keris_A-Line_Batik_Skirt.png", "image/png", (byte[]) null);

        MultipartFile BatikKerisLongBatikDressImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Batik_Keris_Long_Batik_Dress.png", "Batik_Keris_Long_Batik_Dress.png", "image/png", (byte[]) null);

        MultipartFile BatikKerisShortSleeveBatikShirtImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Batik_Keris_Short_Sleeve_Batik_Shirt.png", "Batik_Keris_Short_Sleeve_Batik_Shirt.png", "image/png", (byte[]) null);

        MultipartFile BlackmoresEchinaceaImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Blackmores_Echinacea.png", "Blackmores_Echinacea.png", "image/png", (byte[]) null);

        MultipartFile BlackmoresMultivitaminforAdultsImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Blackmores_Multivitamin_for_Adults.png", "Blackmores_Multivitamin_for_Adults.png", "image/png", (byte[]) null);

        MultipartFile BlackmoresVitaminC1000mgImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Blackmores_Vitamin_C_1000mg.png", "Blackmores_Vitamin_C_1000mg.png", "image/png", (byte[]) null);

        MultipartFile CocaColaClassic330mlImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Coca-Cola_Classic_330ml.png", "Coca-Cola_Classic_330ml.png", "image/png", (byte[]) null);

        MultipartFile CocaColaDiet330mlImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Coca-Cola_Diet_330ml.png", "Coca-Cola_Diet_330ml.png", "image/png", (byte[]) null);

        MultipartFile CokelatChokiChokiImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Cokelat_Choki-Choki.png", "Cokelat_Choki-Choki.png", "image/png", (byte[]) null);

        MultipartFile DecathlonArtengoBR900Image = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Decathlon_Artengo_BR_900.png", "Decathlon_Artengo_BR_900.png", "image/png", (byte[]) null);

        MultipartFile DecathlonDomyosYogaMatImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Decathlon_Domyos_Yoga_Mat.png", "Decathlon_Domyos_Yoga_Mat.png", "image/png", (byte[]) null);

        MultipartFile DecathlonKipstaF500SoccerBallImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Decathlon_Kipsta_F500_Soccer_Ball.png", "Decathlon_Kipsta_F500_Soccer_Ball.png", "image/png", (byte[]) null);

        MultipartFile EigerAdventureCapImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Eiger_Adventure_Cap.png", "Eiger_Adventure_Cap.png", "image/png", (byte[]) null);

        MultipartFile EigerExplorerBackpackImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Eiger_Explorer_Backpack.png", "Eiger_Explorer_Backpack.png", "image/png", (byte[]) null);

        MultipartFile EigerTrailSunglassesImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Eiger_Trail_Sunglasses.png", "Eiger_Trail_Sunglasses.png", "image/png", (byte[]) null);

        MultipartFile EminaBareWithMeMineralPowderImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Emina_Bare_With_Me_Mineral_Powder.png", "Emina_Bare_With_Me_Mineral_Powder.png", "image/png", (byte[]) null);

        MultipartFile EminaBrightStuffFacialWashImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Emina_Bright_Stuff_Facial_Wash.png", "Emina_Bright_Stuff_Facial_Wash.png", "image/png", (byte[]) null);

        MultipartFile EminaCreamyLipCreamImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Emina_Creamy_Lip_Cream.png", "Emina_Creamy_Lip_Cream.png", "image/png", (byte[]) null);

        MultipartFile ErigoClassicHoodieImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Erigo_Classic_Hoodie.png", "Erigo_Classic_Hoodie.png", "image/png", (byte[]) null);

        MultipartFile ErigoGraphicTShirtImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Erigo_Graphic_T-Shirt.png", "Erigo_Graphic_T-Shirt.png", "image/png", (byte[]) null);

        MultipartFile ErigoLightJacketImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Erigo_Light_Jacket.png", "Erigo_Light_Jacket.png", "image/png", (byte[]) null);

        MultipartFile IKEAINGATORPExtendableTableImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/IKEA_INGATORP_Extendable_Table.png", "IKEA_INGATORP_Extendable_Table.png", "image/png", (byte[]) null);

        MultipartFile IKEAKIVIKSofaImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/IKEA_KIVIK_Sofa.png", "IKEA_KIVIK_Sofa.png", "image/png", (byte[]) null);

        MultipartFile IKEASINNERLIGTableLampImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/IKEA_SINNERLIG_Table_Lamp.png", "IKEA_SINNERLIG_Table_Lamp.png", "image/png", (byte[]) null);

        MultipartFile InformaAreaRugImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Informa_Area_Rug.png", "Informa_Area_Rug.png", "image/png", (byte[]) null);

        MultipartFile InformaCurtainSetImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Informa_Curtain_Set.png", "Informa_Curtain_Set.png", "image/png", (byte[]) null);

        MultipartFile InformaThrowPillowImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Informa_Throw_Pillow.png", "Informa_Throw_Pillow.png", "image/png", (byte[]) null);

        MultipartFile KALBEFluCoughSyrupImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/KALBE_Flu___Cough_Syrup.png", "KALBE_Flu___Cough_Syrup.png", "image/png", (byte[]) null);

        MultipartFile KALBEVitaHealthMultivitaminImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/KALBE_VitaHealth_Multivitamin.png", "KALBE_VitaHealth_Multivitamin.png", "image/png", (byte[]) null);

        MultipartFile KALBEVitaminD3Image = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/KALBE_Vitamin_D3.png", "KALBE_Vitamin_D3.png", "image/png", (byte[]) null);

        MultipartFile KopiKapalApiImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Kopi_Kapal_Api.png", "Kopi_Kapal_Api.png", "image/png", (byte[]) null);

        MultipartFile KumpulanKreasiMiniDroneImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Kumpulan_Kreasi_Mini_Drone.png", "Kumpulan_Kreasi_Mini_Drone.png", "image/png", (byte[]) null);

        MultipartFile KumpulanKreasiPaintingSetImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Kumpulan_Kreasi_Painting_Set.png", "Kumpulan_Kreasi_Painting_Set.png", "image/png", (byte[]) null);

        MultipartFile KumpulanKreasiUkuleleSopranoImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Kumpulan_Kreasi_Ukulele_Soprano.png", "Kumpulan_Kreasi_Ukulele_Soprano.png", "image/png", (byte[]) null);

        MultipartFile LGDualInverterAC1PKImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/LG_Dual_Inverter_AC_1_PK.png", "LG_Dual_Inverter_AC_1_PK.png", "image/png", (byte[]) null);

        MultipartFile LGFrontLoadWashingMachine8kgImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/LG_Front_Load_Washing_Machine_8kg.png", "LG_Front_Load_Washing_Machine_8kg.png", "image/png", (byte[]) null);

        MultipartFile LGWatchW7Image = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/LG_Watch_W7.png", "LG_Watch_W7.png", "image/png", (byte[]) null);

        MultipartFile MatoaClassicWoodWatchImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Matoa_Classic_Wood_Watch.png", "Matoa_Classic_Wood_Watch.png", "image/png", (byte[]) null);

        MultipartFile MatoaInstantCameraVintageSeriesImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Matoa_Instant_Camera___Vintage_Series.png", "Matoa_Instant_Camera___Vintage_Series.png", "image/png", (byte[]) null);

        MultipartFile MatoaWoodenModelKitClassicCarImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Matoa_Wooden_Model_Kit___Classic_Car.png", "Matoa_Wooden_Model_Kit___Classic_Car.png", "image/png", (byte[]) null);

        MultipartFile MobiAllSeasonTireImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Mobi_All-Season_Tire.png", "Mobi_All-Season_Tire.png", "image/png", (byte[]) null);

        MultipartFile MobiBatteryCableImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Mobi_Battery_Cable.png", "Mobi_Battery_Cable.png", "image/png", (byte[]) null);

        MultipartFile MobiHelmImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Mobi_helm.png", "Mobi_helm.png", "image/png", (byte[]) null);

        MultipartFile NOSAClassicSunglassesImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/NOSA_Classic_Sunglasses.png", "NOSA_Classic_Sunglasses.png", "image/png", (byte[]) null);

        MultipartFile NOSALeatherBraceletImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/NOSA_Leather_Bracelet.png", "NOSA_Leather_Bracelet.png", "image/png", (byte[]) null);

        MultipartFile NOSASnapbackHatImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/NOSA_Snapback_Hat.png", "NOSA_Snapback_Hat.png", "image/png", (byte[]) null);

        MultipartFile NikeDriFITTeeImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Nike_Dri-FIT_Tee.png", "Nike_Dri-FIT_Tee.png", "image/png", (byte[]) null);

        MultipartFile NikeGymTowelImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Nike_Gym_Towel.png", "Nike_Gym_Towel.png", "image/png", (byte[]) null);

        MultipartFile NikeProTrainingPantsImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Nike_Pro_Training_Pants.png", "Nike_Pro_Training_Pants.png", "image/png", (byte[]) null);

        MultipartFile PertaminaCarBatteryImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Pertamina_Car_Battery.png", "Pertamina_Car_Battery.png", "image/png", (byte[]) null);

        MultipartFile PertaminaCoolantImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Pertamina_Coolant.png", "Pertamina_Coolant.png", "image/png", (byte[]) null);

        MultipartFile PertaminaFastronTechno10WImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Pertamina_Fastron_Techno_10W-40.png", "Pertamina_Fastron_Techno_10W-40.png", "image/png", (byte[]) null);

        MultipartFile SamsungtwoDoorRefrigeratorImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Samsung_2-Door_Refrigerator_330L.png", "Samsung_2-Door_Refrigerator_330L.png", "image/png", (byte[]) null);

        MultipartFile SamsungGalaxyS23Image = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Samsung_Galaxy_S23.png", "Samsung_Galaxy_S23.png", "image/png", (byte[]) null);

        MultipartFile SamsungUHDTVImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Samsung_UHD_TV_50_inch.png", "Samsung_UHD_TV_50_inch.png", "image/png", (byte[]) null);

        MultipartFile SariayuBodyScrubImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sariayu_Body_Scrub___Green_Tea.png", "Sariayu_Body_Scrub___Green_Tea.png", "image/png", (byte[]) null);

        MultipartFile SariayuHandCreamImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sariayu_Hand_Cream___Vitamin_E.png", "Sariayu_Hand_Cream___Vitamin_E.png", "image/png", (byte[]) null);

        MultipartFile SariayuShampooImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sariayu_Shampoo___Herbal.png", "Sariayu_Shampoo___Herbal.png", "image/png", (byte[]) null);

        MultipartFile SidoMunculEchinaceaImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sido_Muncul_Echinacea.png", "Sido_Muncul_Echinacea.png", "image/png", (byte[]) null);

        MultipartFile SidoMunculJamuKunyitAsamImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sido_Muncul_Jamu_Kunyit_Asam.png", "Sido_Muncul_Jamu_Kunyit_Asam.png", "image/png", (byte[]) null);

        MultipartFile SidoMunculVitaminCImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sido_Muncul_Vitamin_C_1000mg.png", "Sido_Muncul_Vitamin_C_1000mg.png", "image/png", (byte[]) null);

        MultipartFile SonyAlphaA7Image = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sony_Alpha_A7_III.png", "Sony_Alpha_A7_III.png", "image/png", (byte[]) null);

        MultipartFile SonyPlayStationImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sony_PlayStation_5.png", "Sony_PlayStation_5.png", "image/png", (byte[]) null);

        MultipartFile SonyWHXMImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sony_WH-1000XM4.png", "Sony_WH-1000XM4.png", "image/png", (byte[]) null);

        MultipartFile SophieParisClassicToteBagImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sophie_Paris_Classic_Tote_Bag.png", "Sophie_Paris_Classic_Tote_Bag.png", "image/png", (byte[]) null);

        MultipartFile SophieParisElegantMaxiDressImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sophie_Paris_Elegant_Maxi_Dress.png", "Sophie_Paris_Elegant_Maxi_Dress.png", "image/png", (byte[]) null);
        MultipartFile SophieParisTrendyBomberJacketImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sophie_Paris_Trendy_Bomber_Jacket.png", "Sophie_Paris_Trendy_Bomber_Jacket.png", "image/png", (byte[]) null);

        MultipartFile SportlineBasketballProImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sportline_Basketball_Pro.png", "Sportline_Basketball_Pro.png", "image/png", (byte[]) null);

        MultipartFile SportlineMountainBikeImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sportline_Mountain_Bike_SL-300.png", "Sportline_Mountain_Bike_SL-300.png", "image/png", (byte[]) null);

        MultipartFile SportlineSkateboardImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sportline_Skateboard_Pro_Series.png", "Sportline_Skateboard_Pro_Series.png", "image/png", (byte[]) null);

        MultipartFile SpriteImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Sprite_330ml.png", "Sprite_330ml.png", "image/png", (byte[]) null);

        MultipartFile WardahCDefenseSerumImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Wardah_C-Defense_Serum.png", "Wardah_C-Defense_Serum.png", "image/png", (byte[]) null);

        MultipartFile WardahHydratingMoisturizerImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Wardah_Hydrating_Moisturizer.png", "Wardah_Hydrating_Moisturizer.png", "image/png", (byte[]) null);

        MultipartFile WardahSunscreenImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/Wardah_Sunscreen_SPF_30.png", "Wardah_Sunscreen_SPF_30.png", "image/png", (byte[]) null);

        MultipartFile BiskuitRomaImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/biskuit_roma.png", "biskuit_roma.png", "image/png", (byte[]) null);

        MultipartFile KecapABCImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/kecapabc.png", "kecapabc.png", "image/png", (byte[]) null);

        MultipartFile SambalABCImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/VendorProducts/sambalabc.png", "sambalabc.png", "image/png", (byte[]) null);


        listProduct.add(NewProductRequest.builder()
                .name("Kecap Manis ABC")
                .category("Food And Beverages")
                .description("Kecap Manis ABC is a premium sweet soy sauce that adds a sweet and umami flavor to dishes. Made with high-quality ingredients, this soy sauce is ideal for enhancing the taste of various dishes such as fried rice and satay.")
                .image(KecapABCImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Sambal ABC")
                .category("Food And Beverages")
                .description("Sambal ABC is a ready-to-use chili sauce that adds a spicy and delicious kick to any dish. It’s perfect for enhancing the flavor of foods like fried snacks, noodles, and rice. Available in a convenient packaging for everyday use.")
                .image(SambalABCImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Coca-Cola Classic 330ml")
                .category("Food And Beverages")
                .description("Coca-Cola Classic is an iconic soda drink with a sweet and carbonated flavor. Widely known around the world, Coca-Cola provides a refreshing and invigorating sensation every time it is consumed.")
                .image(CocaColaClassic330mlImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Coca-Cola Diet 330ml")
                .category("Food And Beverages")
                .description("Coca-Cola Diet offers the classic Coca-Cola taste without the calories from sugar. Ideal for those who want to enjoy the Coca-Cola flavor without added calories, perfect for a healthy lifestyle.")
                .image(CocaColaDiet330mlImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Sprite 330ml")
                .category("Food And Beverages")
                .description("Sprite is a soda drink with a refreshing lemon-lime flavor. With its light and cheerful taste, Sprite is perfect for accompanying various activities and providing a sensation of freshness.")
                .image(SpriteImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Biskuit Roma")
                .category("Food And Beverages")
                .description("Biskuit Roma is a crispy and delicious biscuit made from high-quality ingredients. Suitable for daily snacks or as a companion for tea, this biscuit comes in various flavors that are tempting.")
                .image(BiskuitRomaImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Choki-Choki")
                .category("Food And Beverages")
                .description("Choki-Choki is a chocolate paste that can be enjoyed directly from the package. With its rich chocolate flavor and creamy texture, this product is very popular among children and adults.")
                .image(CokelatChokiChokiImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Kopi Kapal Api")
                .category("Food And Beverages")
                .description("Kopi Kapal Api is premium coffee powder known for its rich flavor and enticing aroma. Ideal for enjoying coffee in the morning or afternoon, providing an authentic and satisfying coffee taste.")
                .image(KopiKapalApiImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Samsung Galaxy S23 Ultra Gray")
                .category("Electronic")
                .description("Samsung Galaxy S23 is a flagship smartphone with a Dynamic AMOLED 2X display, Exynos 2200 processor, and 50MP main camera. It offers high performance and excellent camera features, ideal for users needing a versatile and advanced device.")
                .image(SamsungGalaxyS23Image)
                .build());

        listProduct.add(NewProductRequest.builder().name("Samsung UHD TV 50 inch")
                .category("Electronic")
                .description("Samsung UHD TV 50 inch offers 4K Ultra HD resolution with clear images and vivid colors. The Smart TV feature allows easy access to various streaming applications and online content.")
                .image(SamsungUHDTVImage)
                .build());

        listProduct.add(NewProductRequest.builder().name("Samsung 2-Door Refrigerator 330L")
                .category("Electronic")
                .description("The Samsung 2-Door Refrigerator with a 330-liter capacity features Twin Cooling Plus technology to keep food fresh longer. Its elegant and efficient design makes it suitable for various storage needs.")
                .image(SamsungtwoDoorRefrigeratorImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sony WH-1000XM4")
                .category("Electronic")
                .description("Sony WH-1000XM4 is an over-ear headphone with the best noise-cancelling features. It offers superior audio quality, is comfortable to wear, and is ideal for travel or daily use.")
                .image(SonyWHXMImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sony Alpha A7 III")
                .category("Electronic")
                .description("Sony Alpha A7 III is a full-frame mirrorless camera with a 24.2MP sensor and fast autofocus system. Ideal for professional and amateur photographers who want to produce high-quality images.")
                .image(SonyAlphaA7Image)
                .build());

        listProduct.add(NewProductRequest.builder().name("Sony PlayStation 5")
                .category("Electronic")
                .description("Sony PlayStation 5 is the latest gaming console with high graphics performance and fast loading speeds. It provides an immersive gaming experience with a variety of exclusive games and advanced features.")
                .image(SonyPlayStationImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("LG Front Load Washing Machine 8kg")
                .category("Electronic")
                .description("The LG front load washing machine with an 8kg capacity features TurboWash and 6 Motion Direct Drive technology for cleaner and more efficient washing. Its modern and energy-efficient design makes it an ideal choice for households.")
                .image(LGFrontLoadWashingMachine8kgImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("LG Dual Inverter AC 1 PK")
                .category("Electronic")
                .description("The LG Dual Inverter AC with 1 PK capacity features an inverter technology that efficiently regulates temperature and saves energy. Its sleek design and advanced features provide maximum comfort in your space.")
                .image(LGDualInverterAC1PKImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("LG Watch W7")
                .category("Electronic")
                .description("The LG Watch W7 is a smartwatch with an AMOLED display and advanced features such as activity tracking, notifications, and smartphone integration. Its stylish and functional design suits an active lifestyle.")
                .image(LGWatchW7Image)
                .build());
        listProduct.add(NewProductRequest.builder().name("Batik Keris Long Batik Dress")
                .category("Fashion")
                .description("The Batik Keris Long Batik Dress is a long dress with elegant traditional batik motifs. Made from comfortable cotton fabric, it is suitable for both formal and casual events.")
                .image(BatikKerisLongBatikDressImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Batik Keris Short Sleeve Batik Shirt")
                .category("Fashion")
                .description("The Batik Keris Short Sleeve Batik Shirt features a batik design with short sleeves, suitable for everyday wear. Made from high-quality cotton for extra comfort.")
                .image(BatikKerisShortSleeveBatikShirtImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Batik Keris A-Line Batik Skirt")
                .category("Fashion")
                .description("The Batik Keris A-Line Batik Skirt is a skirt with an A-line cut that gives an elegant impression. It features traditional batik design with light and comfortable fabric.")
                .image(BatikKerisALineBatikSkirtImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Erigo Classic Hoodie")
                .category("Fashion")
                .description("The classic hoodie from Erigo features a comfortable and modern design. Made from high-quality fleece, it is perfect for cold weather.")
                .image(ErigoClassicHoodieImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Erigo Graphic T-Shirt")
                .category("Fashion")
                .description("The graphic T-shirt from Erigo features attractive graphics. Made from soft and comfortable cotton, it is ideal for everyday wear.")
                .image(ErigoGraphicTShirtImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Erigo Light Jacket")
                .category("Fashion")
                .description("The light jacket from Erigo is suitable for layering. Designed with functional features and wind-resistant material.")
                .image(ErigoLightJacketImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sophie Paris Elegant Maxi Dress")
                .category("Fashion")
                .description("The Sophie Paris Elegant Maxi Dress is a maxi dress with a modern and elegant design. Made from lightweight fabric, it provides a chic and stylish look.")
                .image(SophieParisElegantMaxiDressImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sophie Paris Trendy Bomber Jacket")
                .category("Fashion")
                .description("The Sophie Paris Trendy Bomber Jacket offers a trendy casual look with a bomber design. High-quality material and stylish details.")
                .image(SophieParisTrendyBomberJacketImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sophie Paris Classic Tote Bag")
                .category("Fashion")
                .description("The Sophie Paris Classic Tote Bag is a simple yet elegant handbag. Ideal for everyday use with ample space for essential items.")
                .image(SophieParisClassicToteBagImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Eiger Trail Sunglasses")
                .category("Accessories")
                .description("Eiger Trail sunglasses are designed for outdoor activities with UV protection lenses and a durable frame. Perfect for adventures in the great outdoors.")
                .image(EigerTrailSunglassesImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Eiger Adventure Cap")
                .category("Accessories")
                .description("Eiger Adventure Cap features a stylish and comfortable design. Made from breathable materials, it's ideal for sun protection.")
                .image(EigerAdventureCapImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Eiger Explorer Backpack")
                .category("Accessories")
                .description("Eiger Explorer Backpack offers a large capacity with multiple compartments to safely store your items during outdoor activities.")
                .image(EigerExplorerBackpackImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("NOSA Classic Sunglasses")
                .category("Accessories")
                .description("NOSA sunglasses feature a classic design with anti-UV lenses. Ideal for daily use with an elegant appearance.")
                .image(NOSAClassicSunglassesImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("NOSA Snapback Hat")
                .category("Accessories")
                .description("NOSA Snapback Hat has a trendy design with comfortable materials. Perfect for a casual look and outdoor activities.")
                .image(NOSASnapbackHatImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("NOSA Leather Bracelet")
                .category("Accessories")
                .description("NOSA Leather Bracelet features a simple yet stylish design. Made from high-quality genuine leather.")
                .image(NOSALeatherBraceletImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("3Second Retro Sunglasses")
                .category("Accessories")
                .description("3Second Retro Sunglasses offer a vintage design with high-quality lenses. Ideal for a trendy look and eye protection.")
                .image(threeSecondRetroSunglassesImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("3Second Leather Belt")
                .category("Accessories")
                .description("3Second Leather Belt offers an elegant design with high-quality materials. Perfect for adding style to your outfit.")
                .image(threeSecondLeatherBeltImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("3Second Baseball Cap")
                .category("Accessories")
                .description("3Second Baseball Cap features a comfortable design with durable materials. Suitable for various activities and casual styles.")
                .image(threeSecondBaseballCapImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("KALBE VitaHealth Multivitamin")
                .category("Health")
                .description("Complete multivitamin supplement that helps meet daily nutritional needs and boost the immune system.")
                .image(KALBEVitaHealthMultivitaminImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("KALBE Flu & Cough Syrup")
                .category("Health")
                .description("Cough syrup that effectively relieves cough and flu symptoms with active ingredients that work quickly to provide comfort.")
                .image(KALBEFluCoughSyrupImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("KALBE Vitamin D3")
                .category("Health")
                .description("Vitamin D3 supplement to support bone health and the immune system, as well as aid calcium absorption.")
                .image(KALBEVitaminD3Image)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sido Muncul Jamu Kunyit Asam")
                .category("Health")
                .description("Traditional jamu based on turmeric and tamarind that helps boost stamina and digestive health.")
                .image(SidoMunculJamuKunyitAsamImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sido Muncul Vitamin C 1000mg")
                .category("Health")
                .description("Vitamin C supplement to support the immune system and speed up recovery.")
                .image(SidoMunculVitaminCImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sido Muncul Echinacea")
                .category("Health")
                .description("Herbal supplement with Echinacea to help boost the immune system and fight infections.")
                .image(SidoMunculEchinaceaImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Blackmores Multivitamin for Adults")
                .category("Health")
                .description("Multivitamin supplement designed to meet daily nutritional needs and support overall health.")
                .image(BlackmoresMultivitaminforAdultsImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Blackmores Vitamin C 1000mg")
                .category("Health")
                .description("High-dose Vitamin C supplement to boost the immune system and assist in recovery.")
                .image(BlackmoresVitaminC1000mgImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Blackmores Echinacea")
                .category("Health")
                .description("Herbal supplement containing Echinacea to enhance the immune system and help fight infections.")
                .image(BlackmoresEchinaceaImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Autovision LED Headlight Bulb")
                .category("Automotive")
                .description("Car headlights with LED technology that provide brighter illumination and energy efficiency.")
                .image(AutovisionLEDHeadlightBulbImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Autovision Dashboard Cover")
                .category("Automotive")
                .description("Car dashboard cover to protect against sunlight and give the interior a more stylish look.")
                .image(AutovisionDashboardCoverImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Autovision Rearview Camera")
                .category("Automotive")
                .description("Rearview camera with HD image quality to assist with parking and enhance safety.")
                .image(AutovisionRearviewCameraImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Pertamina Fastron Techno 10W-40")
                .category("Automotive")
                .description("Synthetic engine oil with high performance to protect the engine from wear and maintain fuel efficiency.")
                .image(PertaminaFastronTechno10WImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Pertamina Car Battery")
                .category("Automotive")
                .description("Car battery with high durability and reliable performance, suitable for various types of vehicles.")
                .image(PertaminaCarBatteryImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Pertamina Coolant")
                .category("Automotive")
                .description("Radiator coolant that helps maintain engine temperature stability and prevent overheating.")
                .image(PertaminaCoolantImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Mobi All-Season Tire")
                .category("Automotive")
                .description("All-season car tire with high grip and good durability in various weather conditions.")
                .image(MobiAllSeasonTireImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Mobi Half-Face Helmet")
                .category("Automotive")
                .description("Classic design half-face helmet offering standard protection with a retro style.")
                .image(MobiHelmImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Mobi Battery Cable")
                .category("Automotive")
                .description("Durable car battery cable for connecting the battery to the vehicle's electrical system.")
                .image(MobiBatteryCableImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Matoa Classic Wood Watch")
                .category("Hobby")
                .description("A watch with a classic design made from high-quality teak wood.")
                .image(MatoaClassicWoodWatchImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Matoa Wooden Model Kit – Classic Car")
                .category("Hobby")
                .description("A wooden model kit that allows you to assemble a replica of a classic car.")
                .image(MatoaWoodenModelKitClassicCarImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Matoa Instant Camera – Vintage Series")
                .category("Hobby")
                .description(" An instant camera with a vintage design that provides immediate photo results after taking a picture.")
                .image(MatoaInstantCameraVintageSeriesImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Kumpulan Kreasi Soprano Ukulele")
                .category("Hobby")
                .description("A soprano ukulele with clear sound quality and solid wood construction for high durability.")
                .image(KumpulanKreasiUkuleleSopranoImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Kumpulan Kreasi Painting Set")
                .category("Hobby")
                .description("A complete painting set that includes acrylic paints, brushes, and quality canvases.")
                .image(KumpulanKreasiPaintingSetImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Kumpulan Kreasi Mini Drone")
                .category("Hobby")
                .description(" A mini drone with stable flying features and HD camera for recording videos.")
                .image(KumpulanKreasiMiniDroneImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Airsoft Indo M4 Carbine Replica")
                .category("Hobby")
                .description("An airsoft rifle replica of the M4 Carbine with highly detailed resemblance to the original.")
                .image(AirsoftIndoM4CarbineReplicaImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Airsoft Indo BB Pellets – 0.20g")
                .category("Hobby")
                .description("High-quality BB pellets for airsoft with a weight of 0.20 grams, ideal for use with airsoft rifle replicas.")
                .image(AirsoftIndoBBPelletsImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Airsoft Indo Tactical Vest")
                .category("Hobby")
                .description("A tactical vest designed to provide protection and comfort during airsoft games. Equipped with pockets for storing pellets and other gear, and made from durable materials.")
                .image(AirsoftIndoTacticalVestImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Wardah Sunscreen SPF 30")
                .category("Beauty And Cares")
                .description("Sunscreen with SPF 30 that protects the skin from UV exposure and prevents skin damage from the sun. Its formula is light and non-greasy, suitable for daily use.")
                .image(WardahSunscreenImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Wardah C-Defense Serum")
                .category("Beauty And Cares")
                .description("Vitamin C serum designed to brighten the skin and reduce signs of aging.")
                .image(WardahCDefenseSerumImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Wardah Hydrating Moisturizer")
                .category("Beauty And Cares")
                .description("A moisturizer that provides intensive hydration for dry skin. ")
                .image(WardahHydratingMoisturizerImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Emina Creamy Lip Cream")
                .category("Beauty And Cares")
                .description("Lip cream with a lightweight texture and intense color. Features a moisturizing formula and matte finish, suitable for daily wear or special events.")
                .image(EminaCreamyLipCreamImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Emina Bare With Me Mineral Powder")
                .category("Beauty And Cares")
                .description("Mineral powder that provides a matte finish and absorbs excess oil. Features a lightweight formula suitable for all skin types, helping to keep makeup fresh throughout the day.")
                .image(EminaBareWithMeMineralPowderImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Emina Bright Stuff Facial Wash")
                .category("Beauty And Cares")
                .description("Facial cleanser with a gentle formula that cleanses dirt and makeup residue without drying out the skin.")
                .image(EminaBrightStuffFacialWashImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sariayu Body Scrub – Green Tea")
                .category("Beauty And Cares")
                .description("Body scrub containing green tea extract and natural exfoliating beads to remove dead skin cells. Helps to smooth the skin and provides a fresh sensation after use.")
                .image(SariayuBodyScrubImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sariayu Hand Cream – Vitamin E")
                .category("Beauty And Cares")
                .description("Hand cream that moisturizes and nourishes the hands with Vitamin E. Provides protection and softness to the hands, suitable for daily use.")
                .image(SariayuHandCreamImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sariayu Shampoo – Herbal")
                .category("Beauty And Cares")
                .description("Herbal shampoo designed to care for hair and scalp with natural ingredients. Contains herbal plant extracts that help address various hair and scalp issues.")
                .image(SariayuShampooImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("IKEA SINNERLIG Table Lamp")
                .category("Home And Living")
                .description("A table lamp with a minimalist and modern design, perfect for illuminating a work desk or reading space. Made from bamboo with a soft light to create a cozy atmosphere.")
                .image(IKEASINNERLIGTableLampImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("IKEA KIVIK Sofa")
                .category("Home And Living")
                .description("A modern and comfortable sofa, upholstered with easy-to-clean fabric. Ideal for living rooms or family rooms, offering both comfort and style.")
                .image(IKEAKIVIKSofaImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("IKEA INGATORP Extendable Table")
                .category("Home And Living")
                .description("A dining table with an extendable design, classic and elegant. Made from solid wood, perfect for dining with family or guests.")
                .image(IKEAINGATORPExtendableTableImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Ace Hardware Cookware Set")
                .category("Home And Living")
                .description("A set of kitchenware including pots, pans, and other cooking tools. Made from non-stick material, making cooking and cleaning easier.")
                .image(AceHardwareCookwareSetImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Ace Hardware Storage Box")
                .category("Home And Living")
                .description("A versatile storage box with a tight lid, ideal for keeping household items and maintaining home organization.")
                .image(AceHardwareStorageBoxImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Ace Hardware Vacuum Cleaner")
                .category("Home And Living")
                .description(" A vacuum cleaner with strong suction power and an ergonomic design. Facilitates cleaning of floors, carpets, and other surfaces in the home.")
                .image(AceHardwareVacuumCleanerImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Informa Area Rug")
                .category("Home And Living")
                .description("A carpet with an elegant design and soft material. Ideal for adding comfort and beauty to the living room or family room.")
                .image(InformaAreaRugImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Informa Curtain Set")
                .category("Home And Living")
                .description("Curtains with a modern design and high-quality fabric. Provides privacy and controls the amount of light entering the room.")
                .image(InformaCurtainSetImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Informa Throw Pillow")
                .category("Home And Living")
                .description("Decorative throw pillow with high-quality fabric. Adds comfort and enhances the appearance of a sofa or seating area in the home.")
                .image(InformaThrowPillowImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Decathlon Artengo BR 900")
                .category("Sports")
                .description("A badminton racket made of composite materials with an aerodynamic design. Suitable for advanced players with good control and power in their shots.")
                .image(DecathlonArtengoBR900Image)
                .build());
        listProduct.add(NewProductRequest.builder().name("Decathlon Domyos Yoga Mat")
                .category("Sports")
                .description(" A yoga mat with 6mm thickness and anti-slip material. Ideal for yoga, pilates, and stretching exercises at home or in the studio.")
                .image(DecathlonDomyosYogaMatImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Decathlon Kipsta F500 Soccer Ball")
                .category("Sports")
                .description("A soccer ball with high durability and attractive graphic design. Suitable for soccer training and matches.")
                .image(DecathlonKipstaF500SoccerBallImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Nike Dri-FIT Tee")
                .category("Sports")
                .description("A sports t-shirt with Dri-FIT technology that wicks away sweat and keeps you comfortable during physical activities. Stylish design with the Nike logo.")
                .image(NikeDriFITTeeImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Nike Pro Training Pants")
                .category("Sports")
                .description("Training pants made from elastic material with sweat-wicking technology. Suitable for intense workouts and provides maximum comfort.")
                .image(NikeProTrainingPantsImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Nike Gym Towel")
                .category("Sports")
                .description("A gym towel made from soft material with high absorbency. Ideal for use during workouts or as a sports towel.")
                .image(NikeGymTowelImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sportline Mountain Bike SL-300")
                .category("Sports")
                .description("A mountain bike with an aluminum frame and front suspension. Suitable for exploring various terrains with good comfort and control.")
                .image(SportlineMountainBikeImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sportline Skateboard Pro Series")
                .category("Sports")
                .description("A skateboard with a maple wood deck and PU wheels. Ideal for tricks and cruising on the streets.")
                .image(SportlineSkateboardImage)
                .build());
        listProduct.add(NewProductRequest.builder().name("Sportline Basketball Pro")
                .category("Sports")
                .description("A basketball with high durability and good grip. Suitable for training and basketball games.")
                .image(SportlineBasketballProImage)
                .build());

        return listProduct;
    }

    private List<VendorProduct> vendorProducts() {
        List<VendorProduct> vendorProductList = new ArrayList<>();

        vendorProductList.add(VendorProduct.builder()
                .vendor(vendorService.findByName("Indofood"))
                .product(productService.findByName("Kecap Manis ABC"))
                .price(1200000l) //500 bottle
                .stocks(50)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Indofood"))
                .product(productService.findByName("Sambal ABC"))
                .price(1500000l) //400 bottle
                .stocks(60)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Coca Cola"))
                .product(productService.findByName("Coca-Cola Classic 330ml"))
                .price(3500000l) //1000 bottle
                .stocks(100)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Coca Cola"))
                .product(productService.findByName("Coca-Cola Diet 330ml"))
                .price(3700000l) //1000 bottle
                .stocks(90)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Coca Cola"))
                .product(productService.findByName("Sprite 330ml"))
                .price(3600000l) //1000 bottle
                .stocks(120)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Mayora"))
                .product(productService.findByName("Biskuit Roma"))
                .price(2500000l) //300 pcs
                .stocks(300)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Mayora"))
                .product(productService.findByName("Choki-Choki"))
                .price(1800000l) // 400packs
                .stocks(135)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Mayora"))
                .product(productService.findByName("Kopi Kapal Api"))
                .price(2200000l) //200packs
                .stocks(100)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Samsung"))
                .product(productService.findByName("Samsung Galaxy S23 Ultra Gray"))
                .price(12000000l) // 1 unit
                .stocks(45)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Samsung"))
                .product(productService.findByName("Samsung UHD TV 50 inch"))
                .price(8500000l) // 1 unit
                .stocks(50)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Samsung"))
                .product(productService.findByName("Samsung 2-Door Refrigerator 330L"))
                .price(7000000l) //1 unit
                .stocks(30)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sony"))
                .product(productService.findByName("Sony WH-1000XM4"))
                .price(5500000l) //1 unit
                .stocks(75)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sony"))
                .product(productService.findByName("Sony Alpha A7 III"))
                .price(20000000l) //1 unit
                .stocks(10)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sony"))
                .product(productService.findByName("Sony PlayStation 5"))
                .price(9000000l) //1 unit
                .stocks(15)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("LG"))
                .product(productService.findByName("LG Front Load Washing Machine 8kg"))
                .price(6000000l) //1 unit
                .stocks(8)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("LG"))
                .product(productService.findByName("LG Dual Inverter AC 1 PK"))
                .price(4500000l) //1 unit
                .stocks(15)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("LG"))
                .product(productService.findByName("LG Watch W7"))
                .price(3500000l) //1 unit
                .stocks(250)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Batik Keris"))
                .product(productService.findByName("Batik Keris Long Batik Dress"))
                .price(1000000l) //1 unit
                .stocks(150)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Batik Keris"))
                .product(productService.findByName("Batik Keris Short Sleeve Batik Shirt"))
                .price(700000l) //1 unit
                .stocks(90)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Batik Keris"))
                .product(productService.findByName("Batik Keris A-Line Batik Skirt"))
                .price(600000l) //1 unit
                .stocks(200)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Erigo"))
                .product(productService.findByName("Erigo Classic Hoodie"))
                .price(550000l) //1 unit
                .stocks(350)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Erigo"))
                .product(productService.findByName("Erigo Graphic T-Shirt"))
                .price(250000l) //1 unit
                .stocks(460)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Erigo"))
                .product(productService.findByName("Erigo Light Jacket"))
                .price(700000l) //1 unit
                .stocks(305)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sophie Paris"))
                .product(productService.findByName("Sophie Paris Elegant Maxi Dress"))
                .price(950000l) //1 unit
                .stocks(200)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sophie Paris"))
                .product(productService.findByName("Sophie Paris Trendy Bomber Jacket"))
                .price(850000l) //1 unit
                .stocks(345)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sophie Paris"))
                .product(productService.findByName("Sophie Paris Classic Tote Bag"))
                .price(400000l) //1 unit
                .stocks(225)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Eiger"))
                .product(productService.findByName("Eiger Trail Sunglasses"))
                .price(350000l) //1 unit
                .stocks(100)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Eiger"))
                .product(productService.findByName("Eiger Adventure Cap"))
                .price(150000l) //1 unit
                .stocks(155)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Eiger"))
                .product(productService.findByName("Eiger Explorer Backpack"))
                .price(500000l) //1 unit
                .stocks(150)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("NOSA"))
                .product(productService.findByName("NOSA Classic Sunglasses"))
                .price(280000l) //1 unit
                .stocks(35)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("NOSA"))
                .product(productService.findByName("NOSA Snapback Hat"))
                .price(180000l) //1 unit
                .stocks(300)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("NOSA"))
                .product(productService.findByName("NOSA Leather Bracelet"))
                .price(220000l) //1 unit
                .stocks(75)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("3Second"))
                .product(productService.findByName("3Second Retro Sunglasses"))
                .price(330000l) //1 unit
                .stocks(210)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("3Second"))
                .product(productService.findByName("3Second Baseball Cap"))
                .price(160000l) //1 unit
                .stocks(200)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("3Second"))
                .product(productService.findByName("3Second Leather Belt"))
                .price(250000l) //1 unit
                .stocks(340)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("KALBE"))
                .product(productService.findByName("KALBE VitaHealth Multivitamin"))
                .price(130000l) //1 bottle 30 tablets
                .stocks(500)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("KALBE"))
                .product(productService.findByName("KALBE Flu & Cough Syrup"))
                .price(85000l) //1 bottle 100ml
                .stocks(500)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("KALBE"))
                .product(productService.findByName("KALBE Vitamin D3"))
                .price(90000l) //1 bottle 30 capsules
                .stocks(400)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sido Muncul"))
                .product(productService.findByName("Sido Muncul Jamu Kunyit Asam"))
                .price(65000l) //1 bottle 250ml
                .stocks(300)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sido Muncul"))
                .product(productService.findByName("Sido Muncul Vitamin C 1000mg"))
                .price(120000l) //1 bottle 50 tablets
                .stocks(1000)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sido Muncul"))
                .product(productService.findByName("Sido Muncul Echinacea"))
                .price(110000l) //1 bottle 30 capsules
                .stocks(500)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Blackmores"))
                .product(productService.findByName("Blackmores Multivitamin for Adults"))
                .price(175000l) //60 tablets
                .stocks(100)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Blackmores"))
                .product(productService.findByName("Blackmores Vitamin C 1000mg"))
                .price(130000l) //60 tablets
                .stocks(150)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Blackmores"))
                .product(productService.findByName("Blackmores Echinacea"))
                .price(150000l) //30 capsules
                .stocks(200)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Autovision"))
                .product(productService.findByName("Autovision LED Headlight Bulb"))
                .price(450000l) //1 set
                .stocks(500)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Autovision"))
                .product(productService.findByName("Autovision Dashboard Cover"))
                .price(350000l) //1 unit
                .stocks(200)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Autovision"))
                .product(productService.findByName("Autovision Rearview Camera"))
                .price(600000l) //1 unit
                .stocks(50)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Pertamina"))
                .product(productService.findByName("Pertamina Fastron Techno 10W-40"))
                .price(150000l) //1 liter
                .stocks(420)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Pertamina"))
                .product(productService.findByName("Pertamina Car Battery"))
                .price(750000l) //1 unit
                .stocks(45)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Pertamina"))
                .product(productService.findByName("Pertamina Coolant"))
                .price(120000l) //1 liter
                .stocks(600)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Mobi"))
                .product(productService.findByName("Mobi All-Season Tire"))
                .price(1200000l) //1 unit
                .stocks(60)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Mobi"))
                .product(productService.findByName("Mobi Half-Face Helmet"))
                .price(200000l) //1 unit
                .stocks(800)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Mobi"))
                .product(productService.findByName("Mobi Battery Cable"))
                .price(200000l) //1 unit
                .stocks(2500)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Matoa"))
                .product(productService.findByName("Matoa Classic Wood Watch"))
                .price(2000000l) //1 unit
                .stocks(370)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Matoa"))
                .product(productService.findByName("Matoa Wooden Model Kit – Classic Car"))
                .price(350000l) //1 unit
                .stocks(80)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Matoa"))
                .product(productService.findByName("Matoa Instant Camera – Vintage Series"))
                .price(1500000l) //1 unit
                .stocks(70)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Kumpulan Kreasi"))
                .product(productService.findByName("Kumpulan Kreasi Soprano Ukulele"))
                .price(800000l) //1 unit
                .stocks(30)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Kumpulan Kreasi"))
                .product(productService.findByName("Kumpulan Kreasi Painting Set"))
                .price(400000l) //1 set
                .stocks(2100)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Kumpulan Kreasi"))
                .product(productService.findByName("Kumpulan Kreasi Mini Drone"))
                .price(1200000l) //1 unit
                .stocks(30)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Airsoft Indo"))
                .product(productService.findByName("Airsoft Indo M4 Carbine Replica"))
                .price(2500000l) //1 unit
                .stocks(40)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Airsoft Indo"))
                .product(productService.findByName("Airsoft Indo BB Pellets – 0.20g"))
                .price(150000l) //1000 pellets
                .stocks(400)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Airsoft Indo"))
                .product(productService.findByName("Airsoft Indo Tactical Vest"))
                .price(800000l) //1 unit
                .stocks(60)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Wardah"))
                .product(productService.findByName("Wardah C-Defense Serum"))
                .price(250000l) //1 bottle 30ml
                .stocks(240)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Wardah"))
                .product(productService.findByName("Wardah Sunscreen SPF 30"))
                .price(150000l) //1 tube 40ml
                .stocks(320)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Wardah"))
                .product(productService.findByName("Wardah Hydrating Moisturizer"))
                .price(200000l) //1 jar 50ml
                .stocks(140)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Emina"))
                .product(productService.findByName("Emina Creamy Lip Cream"))
                .price(100000l) //1 tube 10ml
                .stocks(350)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Emina"))
                .product(productService.findByName("Emina Bare With Me Mineral Powder"))
                .price(120000l) //1 jar 25g
                .stocks(75)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Emina"))
                .product(productService.findByName("Emina Bright Stuff Facial Wash"))
                .price(80000l) //1 tube 100ml
                .stocks(90)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sariayu"))
                .product(productService.findByName("Sariayu Body Scrub – Green Tea"))
                .price(130000l) //1 jar 200g
                .stocks(330)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sariayu"))
                .product(productService.findByName("Sariayu Hand Cream – Vitamin E"))
                .price(90000l) //1 tube 50ml
                .stocks(95)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sariayu"))
                .product(productService.findByName("Sariayu Shampoo – Herbal"))
                .price(110000l) //1 bottle 250ml
                .stocks(95)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("IKEA Indonesia"))
                .product(productService.findByName("IKEA SINNERLIG Table Lamp"))
                .price(350000l) //1 unit
                .stocks(35)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("IKEA Indonesia"))
                .product(productService.findByName("IKEA KIVIK Sofa"))
                .price(2500000l) //1 unit
                .stocks(20)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("IKEA Indonesia"))
                .product(productService.findByName("IKEA INGATORP Extendable Table"))
                .price(1800000l) //1 unit
                .stocks(15)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Ace Hardware Indonesia"))
                .product(productService.findByName("Ace Hardware Cookware Set"))
                .price(800000l) //1 set 4 items
                .stocks(15)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Ace Hardware Indonesia"))
                .product(productService.findByName("Ace Hardware Storage Box"))
                .price(150000l) //1 unit 30L
                .stocks(25)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Ace Hardware Indonesia"))
                .product(productService.findByName("Ace Hardware Vacuum Cleaner"))
                .price(1200000l) //1 unit
                .stocks(35)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Informa"))
                .product(productService.findByName("Informa Area Rug"))
                .price(700000l) //1 unit
                .stocks(15)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Informa"))
                .product(productService.findByName("Informa Curtain Set"))
                .price(400000l) //1 set 2 panels
                .stocks(50)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Informa"))
                .product(productService.findByName("Informa Throw Pillow"))
                .price(100000l) //1 unit
                .stocks(70)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Decathlon Indonesia"))
                .product(productService.findByName("Decathlon Artengo BR 900"))
                .price(450000l) //1 unit
                .stocks(80)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Decathlon Indonesia"))
                .product(productService.findByName("Decathlon Domyos Yoga Mat"))
                .price(250000l) //1 unit
                .stocks(300)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Decathlon Indonesia"))
                .product(productService.findByName("Decathlon Kipsta F500 Soccer Ball"))
                .price(200000l) //1 unit
                .stocks(40)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Nike Indonesia"))
                .product(productService.findByName("Nike Dri-FIT Tee"))
                .price(350000l) //1 unit
                .stocks(35)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Nike Indonesia"))
                .product(productService.findByName("Nike Pro Training Pants"))
                .price(450000l) //1 unit
                .stocks(15)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Nike Indonesia"))
                .product(productService.findByName("Nike Gym Towel"))
                .price(150000l) //1 unit
                .stocks(20)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sportline Indonesia"))
                .product(productService.findByName("Sportline Mountain Bike SL-300"))
                .price(3500000l) //1 unit
                .stocks(7)
                .build());
        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sportline Indonesia"))
                .product(productService.findByName("Sportline Skateboard Pro Series"))
                .price(900000l) //1 unit
                .stocks(55)
                .build());

        vendorProductList.add(VendorProduct.builder().vendor(vendorService.findByName("Sportline Indonesia"))
                .product(productService.findByName("Sportline Basketball Pro"))
                .price(250000l) //1 unit
                .stocks(80)
                .build());

        return vendorProductList;
    }

    private List<CategoryRequest> categoryRequests(){

        MultipartFile fashionImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/fashion.png", "fashion.png", "image/png", (byte[]) null);

        MultipartFile healthImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/health.png", "health.png", "image/png", (byte[]) null);

        MultipartFile homeAndLivingImage = new MockMultipartFile(" https://ik.imagekit.io/gani88/uploads/Category/homeandliving.png", "homeandliving.png", "image/png", (byte[]) null);

        MultipartFile sportImage = new MockMultipartFile(" https://ik.imagekit.io/gani88/uploads/Category/sport.png", "sport.png", "image/png", (byte[]) null);

        MultipartFile hobbyImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/hobby.png", "hobby.png", "image/png", (byte[]) null);

        MultipartFile beautyAndCaresImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/beautyandcares.png", "beautyandcares.png", "image/png", (byte[]) null);

        MultipartFile accessoriesImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/accessories.png", "accessories.png", "image/png", (byte[]) null);

        MultipartFile electronicImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/electronic.png", "electronic.png", "image/png", (byte[]) null);

        MultipartFile automotiveImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/automotive.png", "automotive.png", "image/png", (byte[]) null);

        MultipartFile foodAndBeveragesImage = new MockMultipartFile("https://ik.imagekit.io/gani88/uploads/Category/foodandbeverages.png", "foodandbeverages.png", "image/png", (byte[]) null);

        return List.of(
                CategoryRequest.builder()
                        .productCategoryName("Fashion")
                        .image(fashionImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Health")
                        .image(healthImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Home And Living")
                        .image(homeAndLivingImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Sports")
                        .image(sportImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Hobby")
                        .image(hobbyImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Beauty And Cares")
                        .image(beautyAndCaresImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Accessories")
                        .image(accessoriesImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Electronic")
                        .image(electronicImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Automotive")
                        .image(automotiveImage)
                        .build(),
                CategoryRequest.builder()
                        .productCategoryName("Food And Beverages")
                        .image(foodAndBeveragesImage)
                        .build()
        );
    }


    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initDataSeeder() {
        if (goodsCategoryService.count() == 0) {
            goodsCategoryService.createBulk(categoryRequests());
        }

        if (productCategoryService.count() == 0) {
            productCategoryService.createBulk(categoryRequests());
        }

        if (productService.count() == 0) {
            productService.createBulkInit(createNewProductRequest());
        }

        if (vendorService.count() == 0) {
            vendorService.createBulk(createNewVendorRequests());
        }

        if (vendorProductService.count() == 0 && productService.count() > 0 && vendorService.count() > 0) {
            vendorProductService.createBulk(vendorProducts());
        }

    }

}
