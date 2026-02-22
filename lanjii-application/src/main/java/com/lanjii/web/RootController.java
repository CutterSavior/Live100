package com.lanjii.web;

import com.lanjii.common.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public R<String> root() {
        return R.success("ok");
    }

    @GetMapping("/healthz")
    public R<String> healthz() {
        return R.success("ok");
    }

    @GetMapping("/status")
    public R<String> status() {
        return R.success("ok");
    }
}
