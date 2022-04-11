package org.prgms.voucher.repository;

import org.prgms.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    public static final File objectFolder = new File("./objects");
    public static final String objPattern = "*.obj";
    private final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + objPattern);

    @Override
    public void save(Voucher voucher) {
        String filename = String.format("./objects/%s.obj", voucher.hashCode());
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(voucher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Voucher> findAll() {
        File[] list = objectFolder.listFiles();
        List<Voucher> vouchers = new ArrayList<>();
        for (File filename : Objects.requireNonNull(list)) {
            if (matcher.matches(filename.toPath().getFileName())) {
                try (FileInputStream fis = new FileInputStream(filename);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Object obj = ois.readObject();
                    vouchers.add((Voucher) obj);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return vouchers;
    }

    @Override
    public void deleteAll() {
        File[] list = objectFolder.listFiles();
        for (File filename : Objects.requireNonNull(list)) {
            if (matcher.matches(filename.toPath().getFileName())) {
                filename.delete();
            }
        }
    }
}
