package com.drartgames.stepper;

import java.io.File;
import java.io.IOException;

public interface ManifestLoader {
    Manifest loadManifest(File file) throws IOException;
}
