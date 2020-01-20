package com.shifat63.spring_boot_reactive.bootstrap;
// Author: Shifat63

import com.shifat63.spring_boot_reactive.model.Brand;
import com.shifat63.spring_boot_reactive.model.Employee;
import com.shifat63.spring_boot_reactive.model.Product;
import com.shifat63.spring_boot_reactive.model.Showroom;
import com.shifat63.spring_boot_reactive.repositories.BrandRepository;
import com.shifat63.spring_boot_reactive.repositories.EmployeeRepository;
import com.shifat63.spring_boot_reactive.repositories.ProductRepository;
import com.shifat63.spring_boot_reactive.repositories.ShowroomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private EmployeeRepository employeeRepository;
    private ShowroomRepository showroomRepository;

    private List<Brand> brandList = new ArrayList<>();
    private List<Showroom> showroomList = new ArrayList<>();

    public InitialDataLoader(ProductRepository productRepository, BrandRepository brandRepository, EmployeeRepository employeeRepository, ShowroomRepository showroomRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.employeeRepository = employeeRepository;
        this.showroomRepository = showroomRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        clearAllData();
        loadBrandData();
        loadShowroomData();
        loadProductData();
        loadEmployeeData();
        brandList.removeAll(brandList);
        showroomList.removeAll(showroomList);
    }

    private void clearAllData() throws Exception
    {
        productRepository.deleteAll();
        brandRepository.deleteAll();
        employeeRepository.deleteAll();
        showroomRepository.deleteAll();
    }

    private void loadBrandData() throws Exception
    {
        //START: Brand data
        Brand lenovo = new Brand();
        lenovo.setName("Lenovo");
        lenovo.setDescription("Lenovo description");
        brandList.add(brandRepository.save(lenovo).block());

        Brand intel = new Brand();
        intel.setName("Intel");
        intel.setDescription("Intel description");
        brandList.add(brandRepository.save(intel).block());

        Brand acer = new Brand();
        acer.setName("Acer");
        acer.setDescription("Acer description");
        brandList.add(brandRepository.save(acer).block());

        Brand dell = new Brand();
        dell.setName("Dell");
        dell.setDescription("Dell description");
        brandList.add(brandRepository.save(dell).block());
        //END: Brand data
    }

    private void loadShowroomData() throws Exception
    {
        //START: Showroom data
        Showroom bonnShowroom = new Showroom();
        bonnShowroom.setName("Bonn Showroom");
        bonnShowroom.setAddress("Bonn showroom address");
        Set<Employee> employeeSetBonn = new HashSet<>();
        showroomList.add(showroomRepository.save(bonnShowroom).block());

        Showroom berlinShowroom = new Showroom();
        berlinShowroom.setName("Berlin Showroom");
        berlinShowroom.setAddress("Berlin showroom address");
        Set<Employee> employeeSetBerlin = new HashSet<>();
        showroomList.add(showroomRepository.save(berlinShowroom).block());
        //END: Showroom data
    }

    private void loadProductData() throws Exception
    {
        //START: Product data
        Product lenovoLaptop = new Product();
        lenovoLaptop.setName("Lenovo Laptop");
        lenovoLaptop.setBrand(brandList.get(0));
        lenovoLaptop.setDescription("Lenovo Laptop description");
        lenovoLaptop.setAvailable(true);
        lenovoLaptop.setPrice(500.65);
        lenovoLaptop.getShowroomSet().add(showroomList.get(0));
        lenovoLaptop.getShowroomSet().add(showroomList.get(1));
        productRepository.save(lenovoLaptop).block();

        Product acerLaptop = new Product();
        acerLaptop.setName("Acer Laptop");
        acerLaptop.setBrand(brandList.get(2));
        acerLaptop.setDescription("Acer Laptop description");
        acerLaptop.setAvailable(true);
        acerLaptop.setPrice(400.75);
        acerLaptop.getShowroomSet().add(showroomList.get(0));
        acerLaptop.getShowroomSet().add(showroomList.get(1));
        productRepository.save(acerLaptop).block();

        Product dellLaptop = new Product();
        dellLaptop.setName("Dell Laptop");
        dellLaptop.setBrand(brandList.get(3));
        dellLaptop.setDescription("Dell Laptop description");
        dellLaptop.setAvailable(true);
        dellLaptop.setPrice(450.50);
        dellLaptop.getShowroomSet().add(showroomList.get(0));
        dellLaptop.getShowroomSet().add(showroomList.get(1));
        productRepository.save(dellLaptop).block();

        Product intelProcessor = new Product();
        intelProcessor.setName("Intel Processor");
        intelProcessor.setBrand(brandList.get(1));
        intelProcessor.setDescription("Intel Processor description");
        intelProcessor.setAvailable(true);
        intelProcessor.setPrice(210.50);
        intelProcessor.getShowroomSet().add(showroomList.get(0));
        intelProcessor.getShowroomSet().add(showroomList.get(1));
        productRepository.save(intelProcessor).block();

        Product intelMotherBoard = new Product();
        intelMotherBoard.setName("Intel MotherBoard");
        intelMotherBoard.setBrand(brandList.get(1));
        intelMotherBoard.setDescription("Intel MotherBoard description");
        intelMotherBoard.setAvailable(false);
        intelMotherBoard.setPrice(110.50);
        intelMotherBoard.getShowroomSet().add(showroomList.get(1));
        productRepository.save(intelMotherBoard).block();

        //END: Product data
    }

    private void loadEmployeeData() throws Exception
    {
        //START: Employee data
        Employee ema = new Employee();
        ema.setFirstName("Khotimakhon");
        ema.setLastName("Ema");
        ema.setIdCardNo("EMA09098");
        ema.setBirthDate(LocalDate.now().minus((24*365), ChronoUnit.DAYS));
        ema.setJoiningDate(LocalDate.now().minus((1*365), ChronoUnit.DAYS));
        ema.setShowroom(showroomList.get(0));
        employeeRepository.save(ema).block();

        Employee zuha = new Employee();
        zuha.setFirstName("Zuha");
        zuha.setLastName("Karim");
        zuha.setIdCardNo("ZUH45654");
        zuha.setBirthDate(LocalDate.now().minus((23*365), ChronoUnit.DAYS));
        zuha.setJoiningDate(LocalDate.now().minus((2*365), ChronoUnit.DAYS));
        zuha.setShowroom(showroomList.get(1));
        employeeRepository.save(zuha).block();

        Employee joki = new Employee();
        joki.setFirstName("Adam");
        joki.setLastName("Zoki");
        joki.setIdCardNo("ZOK34543");
        joki.setBirthDate(LocalDate.now().minus((25*365), ChronoUnit.DAYS));
        joki.setJoiningDate(LocalDate.now().minus((3*365), ChronoUnit.DAYS));
        joki.setShowroom(showroomList.get(0));
        employeeRepository.save(joki).block();

        Employee fish = new Employee();
        fish.setFirstName("Bryan");
        fish.setLastName("Fish");
        fish.setIdCardNo("FIS34093");
        fish.setBirthDate(LocalDate.now().minus((27*365), ChronoUnit.DAYS));
        fish.setJoiningDate(LocalDate.now());
        fish.setShowroom(showroomList.get(1));
        employeeRepository.save(fish).block();
        //END: Employee data
    }
}
