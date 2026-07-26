# 3D Model Setup Guide

## Current Issue
The app crashes with: **"the material was not built for the OpenGL backend"**

This means your `human.glb` file has materials that aren't compatible with the device's GPU backend (OpenGL/Vulkan).

## Solution

### Option 1: Create a Compatible GLB File
If you have Blender, export your model with these settings:
1. Open your human model in Blender
2. Go to **File → Export → glTF 2.0 (.glb/.gltf)**
3. In export options:
   - ✅ Check: `Materials`
   - ✅ Check: `Textures`
   - 📌 Use **Standard** material nodes (not Principled if possible)
   - ✅ Check: `Export Materials` as GLB
4. Save as `human.glb` in `app/src/main/assets/`

### Option 2: Use an Online Converter
1. Go to: https://www.babylonjs-playground.com/
2. Upload your model and re-export with basic materials
3. Save as `human.glb` in `app/src/main/assets/`

### Option 3: Use duck.glb as Test
The `duck.glb` file already works. To verify:
- Temporarily rename `human.glb` to `human.glb.bak`
- Rename `duck.glb` to `human.glb`
- Run the app
- If duck.glb works, your setup is correct; the issue is human.glb's materials

### Option 4: Create from Python (Advanced)
Use `trimesh` to create a simple human mesh:

```python
pip install trimesh pycollada
```

Then run:
```python
import trimesh

# Create simple cylinder for body
body = trimesh.primitives.Cylinder(radius=0.3, height=1.0)
# Create sphere for head
head = trimesh.primitives.Sphere(radius=0.2)
head.apply_translation([0, 0.6, 0])
# Merge
combined = trimesh.util.concatenate([body,head])
combined.export('human.glb')
```

## Technical Details

**Why this happens:**
- Filament (SceneView's rendering engine) has strict material parsing
- Materials created for Vulkan may not work on devices using OpenGL
- Some material features aren't supported across all backends

**What the app does:**
- Tries to load `human.glb` first
- Falls back to `duck.glb` if that fails
- Shows error message if both fail (but doesn't crash)

**Check logs:**
Run and check Android Studio logcat for:
- `D/HumanBody3D: Model loaded successfully: human.glb` ✅
- `E/HumanBody3D: Failed to load...` ⚠️ Will try duck.glb
- `D/HumanBody3D: Fallback model loaded successfully` ✅

## Testing
1. Build and run the app
2. Navigate to HomePage
3. You should see a 3D model (human or duck, depending on compatibility)
4. Check Android logcat for load status


