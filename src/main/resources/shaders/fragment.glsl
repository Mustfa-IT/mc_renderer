#version 330 core
out vec4 FragColor;

in vec3 FragPos;
in vec3 Normal;
in vec2 TexCoord;

uniform vec3 lightPos;
uniform vec3 viewPos;
uniform vec3 lightColor;
uniform sampler2D textureSampler;
uniform int debugMode;

void main()
{
    // Debug mode to visualize texture coordinates
    if (debugMode == 1) {
        FragColor = vec4(TexCoord.x, TexCoord.y, 0.0, 1.0);
        return;
    }

    // Get texture color with improved texture mapping
    vec2 adjustedTexCoord = TexCoord;

    // Make sure texture coordinates are in valid range
    adjustedTexCoord = fract(adjustedTexCoord); // Keep only fractional part (0-1)

    vec4 texColor = texture(textureSampler, adjustedTexCoord);

    // Add a colored pattern if texture is missing
    if (texColor.a < 0.1) {
        // Use a procedural pattern based on position
        float checker = mod(floor(FragPos.x * 2) + floor(FragPos.y * 2) + floor(FragPos.z * 2), 2.0);
        texColor = vec4(checker * 0.8, 0.2, 1.0 - checker * 0.8, 1.0);
    }

    // Ambient
    float ambientStrength = 0.3;
    vec3 ambient = ambientStrength * lightColor;

    // Diffuse
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(lightPos - FragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    // Specular
    float specularStrength = 0.5;
    vec3 viewDir = normalize(viewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * lightColor;

    // Result - use texture color instead of hard-coded color
    vec3 result = (ambient + diffuse + specular) * texColor.rgb;
    FragColor = vec4(result, texColor.a);
}
