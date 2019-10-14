package boot.service.impl;

import boot.service.INoWebService;
import org.springframework.stereotype.Service;

@Service
public class NoWebService implements INoWebService {
    @Override
    public String testService(String word) {
        return word;
    }
}
