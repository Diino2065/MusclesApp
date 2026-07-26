#!/usr/bin/env python3
"""
Generate a simple OpenGL-compatible GLB file of a human figure.
This can be used as a fallback if your existing human.glb has material compatibility issues.

Installation:
    pip install trimesh numpy

Usage:
    python generate_human_glb.py

Output:
    Creates human.glb in the current directory.
"""

try:
    import trimesh
    import numpy as np
except ImportError:
    print("ERROR: trimesh not installed.")
    print("Install with: pip install trimesh numpy")
    exit(1)


def create_simple_human():
    """Create a simple human figure mesh."""
    meshes = []

    # Head
    head = trimesh.primitives.Sphere(radius=0.15)
    head.apply_translation([0, 1.7, 0])
    meshes.append(head)

    # Torso
    torso = trimesh.primitives.Cylinder(radius=0.2, height=0.6)
    torso.apply_translation([0, 1.0, 0])
    meshes.append(torso)

    # Left arm
    left_arm = trimesh.primitives.Cylinder(radius=0.1, height=0.6)
    left_arm.apply_translation([-0.35, 1.1, 0])
    meshes.append(left_arm)

    # Right arm
    right_arm = trimesh.primitives.Cylinder(radius=0.1, height=0.6)
    right_arm.apply_translation([0.35, 1.1, 0])
    meshes.append(right_arm)

    # Left leg
    left_leg = trimesh.primitives.Cylinder(radius=0.12, height=0.8)
    left_leg.apply_translation([-0.15, 0.2, 0])
    meshes.append(left_leg)

    # Right leg
    right_leg = trimesh.primitives.Cylinder(radius=0.12, height=0.8)
    right_leg.apply_translation([0.15, 0.2, 0])
    meshes.append(right_leg)

    # Combine all meshes
    combined = trimesh.util.concatenate(meshes)

    return combined


if __name__ == "__main__":
    print("Generating simple human GLB model...")

    human = create_simple_human()
    human.export("human.glb")

    print(f"✓ Successfully created human.glb")
    print(f"  Vertices: {len(human.vertices)}")
    print(f"  Faces: {len(human.faces)}")
    print()
    print("Installation:")
    print("  1. Copy human.glb to: app/src/main/assets/")
    print("  2. Run the Android app")
    print("  3. The HomePage should now display the 3D model")

