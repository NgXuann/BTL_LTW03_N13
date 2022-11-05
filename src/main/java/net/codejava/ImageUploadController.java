package net.codejava;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageUploadController {
	@GetMapping("/getimage/{image}")
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("image") String image){
		if(image.equals("") || image!=null) {
			try {
				Path filename = Paths.get("uploads",image);
				byte[] buffer = Files.readAllBytes(filename);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(byteArrayResource);
			}
			catch (Exception e){
				//
			}
		}
		return ResponseEntity.badRequest().build();
	}
}
